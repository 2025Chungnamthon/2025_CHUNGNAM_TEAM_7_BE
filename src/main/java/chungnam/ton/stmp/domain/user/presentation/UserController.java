package chungnam.ton.stmp.domain.user.presentation;


import chungnam.ton.stmp.domain.market.dto.response.MarketResponse;
import chungnam.ton.stmp.domain.user.application.UserService;
import chungnam.ton.stmp.global.payload.ResponseCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "유저 정보 관련 API", description = "유저의 개인정보, 서비스 관련 정보를 확인하는 API입니다.")
@RequiredArgsConstructor
@RequestMapping("/api/my")
@RestController
public class UserController {

    private final UserService userService;

    @Operation(summary = "스탬프 현황이 포함된 시장 조회", description = "스탬프를 하나 이상 보유한 시장을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "성공",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = MarketResponse.class))
            )
    )
    @GetMapping("/markets")
    public ResponseCustom<?> getMarketListByUser(
            @RequestHeader("Authorization") String bearerToken,
            @RequestParam(name = "status", defaultValue = "completed") String status
    ) {
        if ("completed".equals(status)) {
            List<MarketResponse> marketResponses = userService.getCompletedMarketByUserId(bearerToken);
            return ResponseCustom.OK(marketResponses);
        } else {
            List<MarketResponse> marketResponses = userService.getNotCompletedMarketByUserId(bearerToken);
            return ResponseCustom.OK(marketResponses);
        }

    }
}
