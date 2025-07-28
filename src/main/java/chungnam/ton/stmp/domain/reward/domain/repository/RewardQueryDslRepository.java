package chungnam.ton.stmp.domain.reward.domain.repository;

import chungnam.ton.stmp.domain.reward.dto.response.RewardResponse;

import java.util.List;

public interface RewardQueryDslRepository {
    List<RewardResponse> findRewardByUserId(Long userId);
}
