package chungnam.ton.stmp.domain.stamp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StampRequestDto {
    private String qrId;
    private Long marketId;
}
