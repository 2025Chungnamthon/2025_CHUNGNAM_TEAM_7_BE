package chungnam.ton.stmp.domain.reward.application;

import chungnam.ton.stmp.domain.has.domain.MarketHasGiftCard;
import chungnam.ton.stmp.domain.has.domain.repository.MarketHasGiftCardRepository;
import chungnam.ton.stmp.domain.market.domain.repository.MarketRepository;
import chungnam.ton.stmp.domain.reward.domain.Reward;
import chungnam.ton.stmp.domain.reward.domain.repository.RewardRepository;
import chungnam.ton.stmp.domain.reward.dto.response.RewardResponse;
import chungnam.ton.stmp.domain.stamp.domain.repository.StampRepository;
import chungnam.ton.stmp.domain.user.domain.User;
import chungnam.ton.stmp.domain.user.domain.repository.UserRepository;
import chungnam.ton.stmp.global.error.DefaultException;
import chungnam.ton.stmp.global.payload.ErrorCode;
import chungnam.ton.stmp.global.util.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RewardService {

    private final RewardRepository rewardRepository;
    private final StampRepository stampRepository;
    private final JwtUtil jwtUtil;
    private final MarketRepository marketRepository;
    private final MarketHasGiftCardRepository marketHasGiftCardRepository;
    private final UserRepository userRepository;

    // userId를 기반으로 유저가 가진 모든 상품권을 조회 및 상세 정보 전달
    public List<RewardResponse> getRewardsByUserId(String rawToken) {
        String token = jwtUtil.getJwt(rawToken);
        Long userId = jwtUtil.getUserIdFromToken(token);
        List<RewardResponse> rewardResponses = rewardRepository.findRewardByUserId(userId);
        return rewardResponses;
    }

    public List<RewardResponse> claimRewardsForMarket(String rawToken, Long marketId) {
        String token = jwtUtil.getJwt(rawToken);
        Long userId = jwtUtil.getUserIdFromToken(token);
        User user = userRepository.findById(userId).orElseThrow(() -> new DefaultException(ErrorCode.USER_NOT_FOUND_ERROR));

        int required = marketRepository.findById(marketId)
                .orElseThrow(() -> new DefaultException(ErrorCode.MARKET_NOT_FOUND_ERROR))
                .getStampAmount();

        int actual = stampRepository.countByUserIdAndMarketId(userId, marketId);

        if (actual < required) {
            throw new DefaultException(ErrorCode.STAMP_NOT_COMPLETE);
        }

        List<MarketHasGiftCard> has = marketHasGiftCardRepository.findAllByMarketId(marketId);

        List<Reward> rewardsToSave = has.stream()
                .map(mg -> Reward.builder()
                        .user(user)
                        .giftCard(mg.getGiftCard())
                        .amount(1)
                        .build()
                )
                .toList();

        rewardRepository.saveAll(rewardsToSave);

        return rewardsToSave.stream()
                .map(r -> RewardResponse.builder()
                        .giftCardName(r.getGiftCard().getName())
                        .cost(r.getGiftCard().getCost())
                        .amount(r.getAmount())
                        .ImageName(r.getGiftCard().getImageName())
                        .imageUrl(r.getGiftCard().getImageUrl())
                        .build())
                .toList();
    }

}
