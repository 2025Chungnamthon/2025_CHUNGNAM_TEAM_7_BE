package chungnam.ton.stmp.domain.favorite;

import chungnam.ton.stmp.domain.favorite.dto.FavRequestDto;
import chungnam.ton.stmp.domain.favorite.dto.FavResponseDto;
import chungnam.ton.stmp.global.payload.ResponseCustom;
import chungnam.ton.stmp.global.util.jwt.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "시장 즐겨찾기 API", description = "유저가 시장에 대해 즐겨찾기 추가 및 삭제를 할 수 있도록 하는 API")
@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
public class FavController {

    private final FavService favService;
    private final JwtUtil jwtUtil;

    // 즐겨찾기 추가
    @Operation(summary = "시장을 즐겨찾기에 추가", description = "JWT 토큰을 통해 유저 정보를 받아 시장을 유저의 즐겨찾기 목록에 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = FavResponseDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @Parameter(name = "request", description = "즐겨찾기 requestDto", required = true)
    @PostMapping
    public ResponseCustom<FavResponseDto> addBookmark(
            @Parameter(description = "Accesstoken을 입력해주세요.", required = true) @RequestHeader("bearerToken") String bearerToken,
            @RequestBody FavRequestDto request
    ) {
        String rawToken = bearerToken.replace("Bearer ", "");
        //JWT 파싱해서 userId 추출
        Long userId = jwtUtil.getUserIdFromToken(rawToken);
        FavResponseDto response = favService.addBookmark(userId, request);

        return ResponseCustom.CREATED(response);
    }

    // 즐겨찾기 삭제
    @Operation(summary = "시장을 즐겨찾기에서 삭제", description = "JWT 토큰을 통해 유저 정보를 받아 시장을 유저의 즐겨찾기 목록에서 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @Parameter(name = "marketId", description = "삭제할 시장의 ID", required = true)
    @DeleteMapping("/{marketId}")
    public ResponseCustom<Void> removeBookmark(
            @Parameter(description = "Accesstoken을 입력해주세요.", required = true) @RequestHeader("bearerToken") String bearerToken,
            @PathVariable Long marketId
    ) {
        String rawToken = bearerToken.replace("Bearer ", "");
        Long userId = jwtUtil.getUserIdFromToken(rawToken);
        favService.removeBookmark(
                userId,
                marketId
        );
        return ResponseCustom.OK();
    }

    @Operation(summary = "시장 즐겨찾기 목록 조회", description = "JWT 토큰을 통해 유저 정보를 받아 유저의 즐겨찾기 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = {
                    @Content(
                        mediaType = "application/json", 
                        array = @ArraySchema(
                            schema = @Schema(implementation = FavResponseDto.class)
                        )
                    )
            }),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @GetMapping
    public ResponseCustom<List<FavResponseDto>> listBookmarks(
            @Parameter(description = "Accesstoken을 입력해주세요.", required = true) @RequestHeader("bearerToken") String bearerToken
    ) {
        String rawToken = bearerToken.replace("Bearer ", "");
        //JWT 파싱해서 userId 추출
        Long userId = jwtUtil.getUserIdFromToken(rawToken);
        List<FavResponseDto> favorites = favService.listBookmarks(
                userId);
        return ResponseCustom.OK(favorites);
    }
}
