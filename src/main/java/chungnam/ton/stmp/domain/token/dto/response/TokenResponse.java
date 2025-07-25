package chungnam.ton.stmp.domain.token.dto.response;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}
