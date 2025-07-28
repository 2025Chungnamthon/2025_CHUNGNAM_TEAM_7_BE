package chungnam.ton.stmp.domain.stamp.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "스탬프 스캔 요청 DTO 입니다")
public record StampRequestDto (
        @Schema(description = "QR 코드 식별자", example = "123456", required = true)
        @NotNull(message = "qrId는 필수입니다.")
        Long qrId){}



