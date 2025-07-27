package chungnam.ton.stmp.domain.qr.generate.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QrCreateRequest {
    private Long marketId;

    private String placeName;
}
