package chungnam.ton.stmp.domain.qr.generate.service;


import chungnam.ton.stmp.domain.qr.generate.MarketPlaceRegistry;
import chungnam.ton.stmp.domain.qr.generate.domain.QrCode;
import chungnam.ton.stmp.domain.market.domain.Market;
import chungnam.ton.stmp.domain.market.domain.repository.MarketRepository;
import chungnam.ton.stmp.domain.qr.generate.domain.repository.QrCodeRepository;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class QrCodeService {
    private  final QrCodeRepository qrCodeRepository;
    private final MarketRepository marketRepository;



    public QrCodeService(QrCodeRepository qrCodeRepository, MarketRepository marketRepository){
        this.qrCodeRepository = qrCodeRepository;
        this.marketRepository = marketRepository;
    }
    String base64Image;

    public QrGenerateResult createQr(Long marketId, String placeName) throws WriterException, IOException {
        // 1. 유효한 마켓 ID인지 확인
        Market market = marketRepository.findById(marketId)
                .orElseThrow(() -> new IllegalArgumentException("해당 marketId에 대한 마켓이 존재하지 않습니다."));

        if (!MarketPlaceRegistry.isValidPlace(marketId, placeName)) {
            throw new IllegalArgumentException("유효하지 않은 장소입니다.");

        }

        int duration = 10;

        QrCode qrCode = qrCodeRepository
                .findByMarket_IdAndPlaceName(marketId, placeName)
                .orElseGet(() -> {
                    // ▶ 최초 생성 시 반드시 NOT NULL 컬럼까지 세팅 후 save 분리
                    QrCode tmp = new QrCode();
                    tmp.setMarket(market);
                    tmp.setPlaceName(placeName);
                    // 임시값 세팅: NOT NULL 제약 있는 필드 모두 초기화
                    tmp.setExpiredAt(LocalDateTime.now().plusSeconds(10)); // 테스트용 만료
                    tmp.setQrImageUrl("");    // 나중에 덮어쓸 임시 문자열
                    tmp.setDuration(duration);
                    return qrCodeRepository.save(tmp);  // 1단계 INSERT: ID 확보 및 NOT NULL 만족
                });

        LocalDateTime createdAt = LocalDateTime.now();
        QrGenerateResult result = new QrGenerateResult(qrCode, base64Image);

        LocalDateTime expiredAt = createdAt.plusSeconds(duration);

        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        int width = 800;
        int height = 800;



        if (qrCode.getExpiredAt() == null || createdAt.isAfter(qrCode.getExpiredAt())) {
            String payload = String.format(
                    "{\"qrId\":\"%s\",\"marketId\":%d,\"placeName\":\"%s\",\"nonce\":\"%s\"}",
                    qrCode.getId(), marketId, placeName, UUID.randomUUID()
            );
            qrCode.setQrImageUrl(payload);
            qrCode.setExpiredAt(createdAt.plusSeconds(duration));
            qrCode.setDuration(duration);
            qrCode = qrCodeRepository.save(qrCode);  // 2단계 UPDATE: 진짜 payload 반영
        }

        // 3. ZXing으로 QR 이미지 생성
        String qrImageUrl = qrCode.getQrImageUrl();
        BitMatrix bitMatrix = new MultiFormatWriter()
                .encode(qrImageUrl, BarcodeFormat.QR_CODE, width, height, hints);
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", out);
            base64Image = Base64.getEncoder().encodeToString(out.toByteArray());
        }


        return new QrGenerateResult(qrCode, base64Image);
    }
}