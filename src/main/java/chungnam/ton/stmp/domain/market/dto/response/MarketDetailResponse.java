package chungnam.ton.stmp.domain.market.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Schema(description = "시장의 상제정보 조회용 response DTO입니다.")
@Getter
@AllArgsConstructor
@Builder
public class MarketDetailResponse {
    private String marketName;
    private String region;
    private String address;
    private String imgUrl;
    private int actual;
    private int required;

    private int facilityCount;
    private List<String> facilities;
}
