package chungnam.ton.stmp.domain.reward.domain.repository;

import chungnam.ton.stmp.domain.reward.domain.Reward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RewardRepository extends JpaRepository<Reward, Long>, RewardQueryDslRepository {

    List<Reward> findAllByUserId(Long userId);
}
