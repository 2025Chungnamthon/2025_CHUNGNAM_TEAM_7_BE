package chungnam.ton.stmp.domain.reward.presentation;

import chungnam.ton.stmp.domain.reward.application.RewardService;
import chungnam.ton.stmp.domain.reward.dto.response.RewardResponse;
import chungnam.ton.stmp.domain.stamp.dto.reponse.StampResponseDto;
import chungnam.ton.stmp.global.payload.ResponseCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "사용자 보유 상품권 API", description = "유저가 가진 상품권 조회나, 상품권 교환 등의 기능을 지원합니다.")
@RestController
@RequestMapping("/api/rewards")
@RequiredArgsConstructor
public class RewardController {

    private final RewardService rewardService;

    @Operation(summary = "상품권 조회", description = "JWT로 인증된 사용자가 보유한 상품권을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "성공",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = StampResponseDto.class)
                    )
            )
    )
    @GetMapping
    public ResponseCustom<?> getAllRewardByUserId(@RequestHeader("Authorization") String bearerToken) {
        List<RewardResponse> rewardResponses = rewardService.getRewardsByUserId(bearerToken);
        return ResponseCustom.OK(rewardResponses);
    }

    @Operation(summary = "상품권 교환", description = "시장의 스탬프를 상품권을 교환합니다.")
    @ApiResponse(responseCode = "200", description = "성공",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = StampResponseDto.class)
                    )
            )
    )
    @Parameter(name = "marketId", description = "마켓 조회용 고유 id", required = true)
    @PostMapping("/{marketId}")
    public ResponseCustom<?> changeStamp2Reward(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable Long marketId
    ) {
        List<RewardResponse> rewardResponses = rewardService.claimRewardsForMarket(bearerToken, marketId);
        return ResponseCustom.OK(rewardResponses);
    }
}
