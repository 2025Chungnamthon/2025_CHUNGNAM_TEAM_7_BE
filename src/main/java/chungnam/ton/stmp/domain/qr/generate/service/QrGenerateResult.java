package chungnam.ton.stmp.domain.qr.generate.service;

import chungnam.ton.stmp.domain.qr.generate.domain.QrCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QrGenerateResult {
    private QrCode qrCode;
    private String base64Image;

}
