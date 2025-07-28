package chungnam.ton.stmp.domain.reward.domain;

import chungnam.ton.stmp.domain.common.BaseEntity;
import chungnam.ton.stmp.domain.gitfCard.domain.GiftCard;
import chungnam.ton.stmp.domain.user.domain.User;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class Reward extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_reward_user"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_reward_user"))
    private GiftCard giftCard;
}
