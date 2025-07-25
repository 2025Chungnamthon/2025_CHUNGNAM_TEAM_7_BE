package chungnam.ton.stmp.qr.generate.service;

import chungnam.ton.stmp.qr.generate.domain.QrCode;
import chungnam.ton.stmp.qr.generate.domain.repository.QrCodeRepository;
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
import java.util.UUID;

@Service
public class QrCodeService {
    private  final QrCodeRepository qrCodeRepository;


    public QrCodeService(QrCodeRepository qrCodeRepository){
        this.qrCodeRepository = qrCodeRepository;
    }

    public QrGenerateResult createQr(Long marketId) throws WriterException, IOException {
        // 1) UUID 기반의 고유 QR ID 생성
        // 2) QR에 담을 문자열(JSON 형태: qrId + marketId) 구성
        // 3) ZXing 라이브러리로 QR 코드 이미지 생성 → PNG → Base64 문자열로 인코딩
        // 4) 생성 시간(createdAt)과 만료 시간(expiredAt = createdAt + duration) 계산
        // 5) QrCode 엔티티 생성 및 DB 저장
        // 6) QrCode 엔티티와 Base64 인코딩된 이미지 데이터를 QrGenerateResult로 반환
        String qrId = UUID.randomUUID().toString();
        String qrImageUrl = String.format(
                "{\"qrId\":\"%s\",\"marketId\":%d}",
                qrId, marketId
        );

        int width = 800; int height = 800;
        BitMatrix bitMatrix = new MultiFormatWriter()
                .encode(qrImageUrl,BarcodeFormat.QR_CODE,width,height);
        String base64Image;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", out);
            base64Image = Base64.getEncoder().encodeToString(out.toByteArray());

        }

        int duration = 1;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiredAt = createdAt.plusMinutes(duration);

        QrCode qrCode = new QrCode();
        qrCode.setQrId(qrId);
        qrCode.setQrImageUrl(qrImageUrl);
        qrCode.setCreatedAt(createdAt);
        qrCode.setExpiredAt(expiredAt);
        qrCode.setDuration(duration);
        qrCode.setMarketId(marketId);
        qrCodeRepository.save(qrCode);

        return new QrGenerateResult(qrCode,base64Image);


    }


}
