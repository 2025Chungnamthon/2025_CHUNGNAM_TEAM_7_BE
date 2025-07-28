package chungnam.ton.stmp.domain.stamp.domain.repository;

import chungnam.ton.stmp.domain.favorite.QFav;
import chungnam.ton.stmp.domain.has.domain.QMarketHasGiftCard;
import chungnam.ton.stmp.domain.market.domain.QMarket;
import chungnam.ton.stmp.domain.market.dto.response.MarketResponse;
import chungnam.ton.stmp.domain.market.dto.response.QMarketResponse;
import chungnam.ton.stmp.domain.qr.generate.domain.QQrCode;
import chungnam.ton.stmp.domain.reward.domain.QReward;
import chungnam.ton.stmp.domain.stamp.domain.QStamp;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class StampQueryDslRepositoryImpl implements StampQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public int countByUserIdAndMarketId(Long userId, Long marketId) {
        QStamp stamp = QStamp.stamp;
        QQrCode qrCode = QQrCode.qrCode;

        return Objects.requireNonNull(queryFactory
                .select(stamp.count())
                .from(stamp)
                .join(stamp.qrCode, qrCode)
                .where(
                        stamp.user.id.eq(userId),
                        qrCode.market.id.eq(marketId)
                )
                .fetchOne()).intValue();
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

    @Override
    public List<MarketResponse> findMarketsByUserStamps(Long userId) {
        QStamp stamp = QStamp.stamp;
        QQrCode qrCode = QQrCode.qrCode;
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
                .from(stamp)
                .join(stamp.qrCode, qrCode)        // stamp → qrCode
                .join(qrCode.market, market)       // qrCode → market
                .leftJoin(fav).on(fav.user.id.eq(userId)
                        .and(fav.market.eq(market)))
                .where(stamp.user.id.eq(userId))
                .distinct()
                .fetch();
    }
}