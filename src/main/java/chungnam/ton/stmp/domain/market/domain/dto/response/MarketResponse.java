package chungnam.ton.stmp.domain.market.domain.dto.response;

import chungnam.ton.stmp.domain.market.domain.Market;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "시장의 조회용 response DTO입니다.")
public record MarketResponse(
        String marketName,
        String region,
        String address
) {
    public static MarketResponse fromEntity(Market market) {
        return MarketResponse.builder()
                .marketName(market.getMarketName())
                .region(market.getRegion())
                .address(market.getAddress())
                .build();
    }
}
