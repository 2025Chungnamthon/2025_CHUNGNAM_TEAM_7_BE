package chungnam.ton.stmp.domain.favorite.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "즐겨찾기 API respons DTO입니다.")
public class FavResponseDto {

    private Long favId;
    private Long marketId;
    private String marketName;

}
