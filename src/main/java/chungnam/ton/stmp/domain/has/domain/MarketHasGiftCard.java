package chungnam.ton.stmp.domain.has.domain;

import chungnam.ton.stmp.domain.common.BaseEntity;
import chungnam.ton.stmp.domain.gitfCard.domain.GiftCard;
import chungnam.ton.stmp.domain.market.domain.Market;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class MarketHasGiftCard extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "market_id", nullable = false,
        foreignKey = @ForeignKey(name = "fk_has_market")
    )
    private Market market;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gift_card_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_has_gift_card"))
    private GiftCard giftCard;
}
