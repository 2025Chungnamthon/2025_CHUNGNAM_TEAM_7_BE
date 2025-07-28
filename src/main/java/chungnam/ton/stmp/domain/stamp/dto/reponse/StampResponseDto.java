package chungnam.ton.stmp.domain.stamp.dto.reponse;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "스탬프 스캔 응답 DTO 입니다")
@Getter
public class StampResponseDto {

    @Schema(description = "스캔한 QR 코드 ID", example = "1")
    private Long qrId;

    @Schema(description = "응답 메시지", example = "스탬프가 성공적으로 저장되었습니다.")
    private String message;

    @Schema(description = "스캔 시간(서버에서자동)", example = "2023-10-01T12:00:00")
    private LocalDateTime scanTime;

    @Schema(description = "시장 ID", example = "7")
    private Long marketId;

    @Schema(description = "시장 이름", example = "중앙시장")
    private String marketName;

    @Schema(description = "장소 이름", example = "입구")
    private String placeName;

    @Schema(description = "총 스탬프 개수", example = "5")
    private int totalStampCount;

    @Builder
    public StampResponseDto(Long qrId, String message, LocalDateTime scanTime, Long marketId,
            String marketName,
            String placeName, int totalStampCount) {
        this.qrId = qrId;
        this.message = message;
        this.scanTime = scanTime;
        this.marketId = marketId;
        this.marketName = marketName;
        this.placeName = placeName;
        this.totalStampCount = totalStampCount;
    }
}
