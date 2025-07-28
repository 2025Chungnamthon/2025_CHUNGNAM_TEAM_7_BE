package chungnam.ton.stmp.domain.stamp.presentation;

import chungnam.ton.stmp.domain.stamp.application.StampService;
import chungnam.ton.stmp.domain.stamp.dto.reponse.StampResponseDto;
import chungnam.ton.stmp.domain.stamp.dto.request.StampRequestDto;
import chungnam.ton.stmp.global.payload.ResponseCustom;
import chungnam.ton.stmp.global.util.jwt.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "스탬프 API", description = "qr 스캔 후 스탬프 적립 API입니다.")
@RestController
@RequestMapping("/api/stamps")
@RequiredArgsConstructor
public class StampController {

    private final StampService stampService;
    private final JwtUtil jwtUtil;

    @Operation(summary = "스탬프 저장 (Scan)", description = "JWT로 인증된 사용자가 QR 코드를 스캔해 스탬프를 저장합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "저장 성공",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StampResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 입력"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @PostMapping("/scan")
    public ResponseCustom<StampResponseDto> scanStamp(@RequestHeader("Authorization") String bearerToken, @Valid @RequestBody StampRequestDto request) {
        String token = jwtUtil.getJwt(bearerToken);
        Long userId = jwtUtil.getUserIdFromToken(token);

        StampResponseDto dto = stampService.scanStamp(userId, request.qrId());
        return ResponseCustom.OK(dto);
    }
}
