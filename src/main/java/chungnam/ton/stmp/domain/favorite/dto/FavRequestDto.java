package chungnam.ton.stmp.domain.favorite.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "즐겨찾기 추가용 request DTO입니다.")
public class FavRequestDto {
    private Long marketId;
}
