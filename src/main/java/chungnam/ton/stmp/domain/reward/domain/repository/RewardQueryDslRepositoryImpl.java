package chungnam.ton.stmp.domain.reward.domain.repository;

import chungnam.ton.stmp.domain.favorite.QFav;
import chungnam.ton.stmp.domain.gitfCard.domain.QGiftCard;
import chungnam.ton.stmp.domain.has.domain.QMarketHasGiftCard;
import chungnam.ton.stmp.domain.market.domain.QMarket;
import chungnam.ton.stmp.domain.market.dto.response.MarketResponse;
import chungnam.ton.stmp.domain.market.dto.response.QMarketResponse;
import chungnam.ton.stmp.domain.reward.domain.QReward;
import chungnam.ton.stmp.domain.reward.dto.response.QRewardResponse;
import chungnam.ton.stmp.domain.reward.dto.response.RewardResponse;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RewardQueryDslRepositoryImpl implements RewardQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<RewardResponse> findRewardByUserId(Long userId) {
        QReward reward = QReward.reward;
        QGiftCard giftCard = QGiftCard.giftCard;

        return queryFactory
                .select(new QRewardResponse(
                        giftCard.name,
                        giftCard.cost,
                        reward.amount,
                        giftCard.ImageName,
                        giftCard.imageUrl
                ))
                .from(reward)
                .join(reward.giftCard, giftCard)
                .where(reward.user.id.eq(userId))
                .fetch();
    }

    @Override
    public List<MarketResponse> findMarketsByUserRewards(Long userId) {
        QReward reward = QReward.reward;
        QMarketHasGiftCard mg = QMarketHasGiftCard.marketHasGiftCard;
        QMarket market = QMarket.market;
        QFav fav = QFav.fav;

        return queryFactory
                .select(new QMarketResponse(
                        market.id,
                        market.marketName,
                        market.region,
                        market.address,
                        fav.id.isNotNull(),
                        Expressions.asBoolean(true)
                ))
                .from(reward)
                .join(reward.giftCard)          // reward → giftCard
                .join(mg).on(mg.giftCard.eq(reward.giftCard))  // giftCard → MarketHasGiftCard
                .join(mg.market, market)        // MarketHasGiftCard → Market
                .leftJoin(fav).on(fav.user.id.eq(userId)
                        .and(fav.market.eq(market)))
                .where(reward.user.id.eq(userId))
                .distinct()
                .fetch();
    }
}
