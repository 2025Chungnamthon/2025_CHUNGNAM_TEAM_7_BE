package chungnam.ton.stmp.domain.market.domain.repository;

import chungnam.ton.stmp.domain.market.domain.Market;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarketRepository extends JpaRepository<Market, String> {
    List<Market> findByMarketNameContainingOrRegionContaining(String marketName, String region);
}
