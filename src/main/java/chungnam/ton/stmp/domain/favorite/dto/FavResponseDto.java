package chungnam.ton.stmp.domain.favorite.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FavResponseDto {

    private Long favId;
    private Long marketId;
    private String marketName;

}
