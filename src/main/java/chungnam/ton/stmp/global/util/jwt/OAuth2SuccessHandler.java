package chungnam.ton.stmp.global.util.jwt;


import chungnam.ton.stmp.domain.token.domain.RefreshToken;
import chungnam.ton.stmp.domain.token.domain.repository.RefreshTokenRepository;
import chungnam.ton.stmp.domain.user.domain.UserPrincipal;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String accessToken = jwtUtil.generateToken(
                userPrincipal.getUser().getId(),
                userPrincipal.getUsername(),
                userPrincipal.getUser().getEmail(),
                userPrincipal.getUser().getRole().getValue()
        );

        String rawRefreshToken = jwtUtil.generateRefreshToken(userPrincipal.getUser().getId());

        // 기존 토큰 조회
        refreshTokenRepository.findByUserId(userPrincipal.getUser().getId()).ifPresent(refreshTokenRepository::delete);

        // DB에 Refresh Token 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .token(rawRefreshToken)
                .userId(userPrincipal.getUser().getId())
                .expiryDate(LocalDateTime.now().plusDays(7))
                .build();

        refreshTokenRepository.save(refreshToken);

        // 리프레시 토큰을 HttpOnly 쿠키로 설정
        Cookie cookie = new Cookie("refreshToken", rawRefreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 7); // 7일
        response.addCookie(cookie);

        // 액세스 토큰은 JSON 바디로 반환
        response.setContentType("application/json;charset=UTF-8");
        String body = "{\"accessToken\":\"" + accessToken + "\"}";
        response.getWriter().write(body);
    }
}
