package chungnam.ton.stmp.domain.has.domain.repository;

import chungnam.ton.stmp.domain.has.domain.MarketHasGiftCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarketHasGiftCardRepository extends JpaRepository<MarketHasGiftCard, Long> {

    List<MarketHasGiftCard> findAllByMarketId(Long marketId);
}
