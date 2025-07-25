package chungnam.ton.stmp.qr.generate.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QrCreateRequest {
    @NotNull(message = "marketId는 필수 입력입니다.")
    private Long marketId;
}
