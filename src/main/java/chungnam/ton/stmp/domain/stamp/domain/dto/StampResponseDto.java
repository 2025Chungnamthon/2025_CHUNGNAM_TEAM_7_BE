package chungnam.ton.stmp.domain.stamp.domain.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StampResponseDto {
    private String message;
    private LocalDateTime scanTime;
    private Long marketId;
    private String marketName;
    private String placeName;
}
