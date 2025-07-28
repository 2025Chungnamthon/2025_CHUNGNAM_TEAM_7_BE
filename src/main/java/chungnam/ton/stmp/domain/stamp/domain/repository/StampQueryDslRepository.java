package chungnam.ton.stmp.domain.stamp.domain.repository;

import chungnam.ton.stmp.domain.market.dto.response.MarketResponse;

import java.util.List;

public interface StampQueryDslRepository {
    int countByUserIdAndMarketId(Long userId, Long marketId);
    List<MarketResponse> findMarketsByUserRewards(Long userId);
    List<MarketResponse> findMarketsByUserStamps(Long userId);
}
