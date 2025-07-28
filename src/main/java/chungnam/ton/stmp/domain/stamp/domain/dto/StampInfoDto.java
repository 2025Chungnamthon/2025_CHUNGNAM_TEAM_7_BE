package chungnam.ton.stmp.domain.stamp.domain.dto;


import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StampInfoDto {
    private String qrId;            // 어떤 QR을 찍었는지
    private LocalDateTime scanTime;
    private Long marketId;
    private String placeName;
}
