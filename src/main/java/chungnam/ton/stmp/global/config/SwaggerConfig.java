package chungnam.ton.stmp.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("충남톤 도장찍어가유 프로젝트 API")
                .description("충남톤 칠면조팀의 스탬프 투어 기반 전통시장 활성화 앱 프로젝트 API 문서")
                .version("1.0");

        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        // Kakao OAuth2 인증 스킴
        SecurityScheme oauthScheme = new SecurityScheme()
                .type(SecurityScheme.Type.OAUTH2)
                .description("Kakao OAuth2 flow for obtaining access token")
                .flows(new OAuthFlows()
                        .authorizationCode(new OAuthFlow()
                                .authorizationUrl("https://kauth.kakao.com/oauth/authorize")
                                .tokenUrl("https://kauth.kakao.com/oauth/token")
                                .scopes(new Scopes()
                                        .addString("account_email", "이메일 조회 권한")
                                        .addString("profile_nickname", "프로필 닉네임 조회 권한")
                                )
                        )
                );

        // 보안 요구사항 설정: JWT 또는 OAuth2
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("BearerAuth")
                .addList("KakaoOAuth");

        return new OpenAPI()
                .info(info)
                .addSecurityItem(securityRequirement)
                .schemaRequirement("BearerAuth", securityScheme)
                .schemaRequirement("KakaoOAuth", oauthScheme);
    }
}
