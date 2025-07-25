package chungnam.ton.stmp.domain.token.application;

import chungnam.ton.stmp.domain.token.domain.RefreshToken;
import chungnam.ton.stmp.domain.token.domain.repository.RefreshTokenRepository;
import chungnam.ton.stmp.domain.token.dto.response.TokenResponse;
import chungnam.ton.stmp.domain.user.domain.User;
import chungnam.ton.stmp.domain.user.domain.repository.UserRepository;
import chungnam.ton.stmp.global.error.DefaultException;
import chungnam.ton.stmp.global.payload.ErrorCode;
import chungnam.ton.stmp.global.util.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class TokenRefreshService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public TokenResponse refreshAccessToken(String rawRefreshToken) {
        // 1) JWT 서명/만료 검증
        if (!jwtUtil.validateToken(rawRefreshToken)) {
            throw new DefaultException(ErrorCode.JWT_EXPIRED_ERROR, "리프레시 토큰이 만료되었거나 유효하지 않습니다.");
        }

        // 2) 토큰에서 userId 파싱
        Long userId = Long.valueOf(jwtUtil.getUserIdFromToken(rawRefreshToken));

        // 3) DB에서 리프레시 토큰 엔티티 조회
        RefreshToken entity = refreshTokenRepository
                .findByUserId(userId)
                .orElseThrow(() -> new DefaultException(ErrorCode.REFRESH_TOKEN_NOT_FOUND, "저장된 리프레시 토큰이 없습니다."));

        // 4) DB 만료일 체크
        if (entity.getExpiryDate().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(entity);
            throw new DefaultException(ErrorCode.JWT_EXPIRED_ERROR, "저장된 리프레시 토큰이 만료되었습니다.");
        }

        // 5) 새 액세스 토큰 발급
        User user = userRepository.findById(userId).orElseThrow(() -> new DefaultException(ErrorCode.USER_NOT_FOUND_ERROR));

        String newAccessToken = jwtUtil.generateToken(
                userId,
                user.getUsername(),
                user.getEmail(),
                user.getRole().getValue()
        );

        // 6) (옵션) 리프레시 토큰도 재발급하려면 아래 실행
        String newRefreshToken = jwtUtil.generateRefreshToken(user.getId());
        long refreshMs = jwtUtil.getRefreshTokenExpirationInMs();
        LocalDateTime newExpiry = LocalDateTime.now().plus(Duration.ofMillis(refreshMs));
        entity.updateToken(newRefreshToken, newExpiry);

        refreshTokenRepository.save(entity);

        return new TokenResponse(newAccessToken, newRefreshToken);
    }
}
