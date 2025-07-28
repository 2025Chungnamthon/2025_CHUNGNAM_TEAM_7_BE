package chungnam.ton.stmp.domain.market.domain.controller;

import chungnam.ton.stmp.domain.favorite.dto.FavResponseDto;
import chungnam.ton.stmp.domain.market.domain.dto.request.SearchMarketDetailRequest;
import chungnam.ton.stmp.domain.market.domain.dto.request.SearchMarketRequest;
import chungnam.ton.stmp.domain.market.domain.dto.response.MarketDetailResponse;
import chungnam.ton.stmp.domain.market.domain.dto.response.MarketResponse;
import chungnam.ton.stmp.domain.market.domain.service.MarketDetailService;
import chungnam.ton.stmp.domain.market.domain.service.SearchMarketService;
import chungnam.ton.stmp.global.payload.ResponseCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "시장 관련 API", description = "앱내 스탬프 투어가 가능한 시장의 정보를 조회하는 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/markets")
public class MarketController {

    private final MarketDetailService marketDetailService;
    private final SearchMarketService marketService;

    @Operation(summary = "시장의 상세 정보 조회", description = "시장의 주소, 편의시설 등 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = FavResponseDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @Parameter(name = "request", description = "즐겨찾기 requestDto", required = true)
    @GetMapping("/detail")
    public ResponseCustom<?> getMarketDetail(@RequestBody SearchMarketDetailRequest searchMarketDetailRequest) {
        MarketDetailResponse response = marketDetailService.getMarketDetailById(searchMarketDetailRequest);

        return ResponseCustom.OK(response);
    }

    @Operation(summary = "시장 목록 조회", description = "DB 내에 저장된 전통시장 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = MarketResponse.class)
                            ))
            }),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @GetMapping("/search")
    public ResponseCustom<?> searchMarkets(@RequestBody SearchMarketRequest searchMarketRequest) {
        List<MarketResponse> responses = marketService.searchMarkets(searchMarketRequest);
        return ResponseCustom.OK(responses);
//        return marketService.searchMarkets(searchMarketRequest);
    }
}
