package chungnam.ton.stmp.domain.favorite.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "즐겨찾기 추가용 request DTO입니다.")
public class FavRequestDto {
    private Long marketId;
}
