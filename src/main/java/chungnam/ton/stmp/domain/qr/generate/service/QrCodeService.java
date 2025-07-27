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
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class QrCodeService {
    private  final QrCodeRepository qrCodeRepository;
    private final MarketRepository marketRepository;



    public QrCodeService(QrCodeRepository qrCodeRepository, MarketRepository marketRepository){
        this.qrCodeRepository = qrCodeRepository;
        this.marketRepository = marketRepository;
    }

    public QrGenerateResult createQr(Long marketId, String placeName) throws WriterException, IOException {
        // 1. 유효한 마켓 ID인지 확인
        Market market = marketRepository.findById(marketId)
                .orElseThrow(() -> new IllegalArgumentException("해당 marketId에 대한 마켓이 존재하지 않습니다."));

        if (!MarketPlaceRegistry.isValidPlace(marketId, placeName)) {
            throw new IllegalArgumentException("유효하지 않은 장소입니다.");
        }

        // 2. 고유 QR ID 생성 및 JSON 형식의 페이로드 구성
        String qrId = UUID.randomUUID().toString();
        String qrImageUrl = String.format(
                "{\"qrId\":\"%s\",\"marketId\":%d,\"placeName\":\"%s\"}",
                qrId, marketId, placeName
        );

        // 3. ZXing으로 QR 이미지 생성
        int width = 800; int height = 800;

        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        BitMatrix bitMatrix = new MultiFormatWriter()
                .encode(qrImageUrl,BarcodeFormat.QR_CODE,width,height,hints);
        String base64Image;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", out);
            base64Image = Base64.getEncoder().encodeToString(out.toByteArray());

        }
        marketId = 1L;

        // 4. QR 코드 엔티티 생성 및 저장
        int duration = 1;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiredAt = createdAt.plusMinutes(duration);

        QrCode qrCode = new QrCode();
        qrCode.setQrId(qrId);
        qrCode.setQrImageUrl(qrImageUrl);
        qrCode.setCreatedAt(createdAt);
        qrCode.setExpiredAt(expiredAt);
        qrCode.setDuration(duration);
        //qrCode.setMarket(market);
        qrCode.setPlaceName(placeName);

        qrCode.setMarketId(marketId);

        qrCodeRepository.save(qrCode);

        return new QrGenerateResult(qrCode,base64Image);


    }


}
