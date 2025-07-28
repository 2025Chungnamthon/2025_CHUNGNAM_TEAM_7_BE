package chungnam.ton.stmp.domain.reward.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;

@Builder
public record RewardResponse(
        String giftCardName,
        int cost,
        int amount,
        String ImageName,
        String imageUrl
) {
    @QueryProjection
    public RewardResponse {}
}
