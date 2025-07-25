package chungnam.ton.stmp.qr.generate.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class QrCreateResponse {
    private String base64QrImage;
    private LocalDateTime expiredAt;
}
