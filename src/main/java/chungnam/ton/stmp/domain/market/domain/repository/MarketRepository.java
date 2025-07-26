package chungnam.ton.stmp.domain.market.domain.repository;

import chungnam.ton.stmp.domain.market.domain.Market;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MarketRepository extends JpaRepository<Market, Long> {
    List<Market> findByMarketNameContainingOrRegionContaining(String marketName, String region);
    Optional<Market> findById(Long id);
}
