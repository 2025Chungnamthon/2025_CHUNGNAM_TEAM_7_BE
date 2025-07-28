package chungnam.ton.stmp.domain.market.dto.response;

import chungnam.ton.stmp.domain.market.domain.Market;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "시장의 조회용 response DTO입니다.")
public record MarketResponse(
        Long marketId,
        String marketName,
        String region,
        String address,
        boolean bookmarked,
        boolean isAllCollected
) {
    @QueryProjection
    public MarketResponse {}

    public static MarketResponse fromEntity(Market market) {
        return MarketResponse.builder()
                .marketId(market.getId())
                .marketName(market.getMarketName())
                .region(market.getRegion())
                .address(market.getAddress())
                .build();
    }
}
