package chungnam.ton.stmp.domain.token.presentation;

import chungnam.ton.stmp.domain.token.application.TokenRefreshService;
import chungnam.ton.stmp.domain.token.dto.response.AccessResponse;
import chungnam.ton.stmp.domain.token.dto.response.TokenResponse;
import chungnam.ton.stmp.global.payload.ErrorCode;
import chungnam.ton.stmp.global.payload.ResponseCustom;
import chungnam.ton.stmp.global.util.jwt.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/token")
@RequiredArgsConstructor
@Tag(name = "refreshToken 발급 컨트롤러", description = "유저의 정보를 확인할 때, refreshToken을 재발급하는 컨트롤러입니다.")
public class TokenRefreshController {

    private final TokenRefreshService tokenRefreshService;
    private final JwtUtil jwtUtil;

    @Operation(summary = "리프레시 토큰을 사용해 새 액세스 토큰 발급")
    @PostMapping("/refresh")
    public ResponseCustom<?> refresh(
            @CookieValue(name = "refreshToken", required = false) String refreshToken,
            HttpServletResponse response
//            @Parameter(description = "Accesstoken을 입력해주세요.", required = true) @RequestHeader("Authorization") String bearerToken
    ) {
        if (refreshToken == null) {
            return ResponseCustom.BAD_REQUEST(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
        }

        // 서비스에서 새 토큰 발급
        TokenResponse tokens = tokenRefreshService.refreshAccessToken(refreshToken);

        // 쿠키로 새 리프레시 토큰 세팅
        Cookie cookie = new Cookie("refreshToken", tokens.refreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge((int) (jwtUtil.getRefreshTokenExpirationInMs() / 1000));
        response.addCookie(cookie);

        return ResponseCustom.OK(new AccessResponse(tokens.accessToken()));
    }
}
