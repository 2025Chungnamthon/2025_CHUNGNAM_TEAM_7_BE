package chungnam.ton.stmp.global.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class QrSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())          // CSRF 비활성화
                .formLogin(login -> login.disable())   // 폼 로그인 비활성화
                .httpBasic(basic -> basic.disable())   // HTTP Basic 인증 비활성화
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // ✅ CORS 설정
                .authorizeHttpRequests(auth -> auth
                        //  QR 생성 관련 POST, GET 둘 다 허용
                        .requestMatchers(HttpMethod.POST, "/qr/generate").permitAll()
                        .requestMatchers(HttpMethod.GET, "/qr/generate").permitAll()

                        //  QR 페이지 및 리소스 GET 허용
                        .requestMatchers(HttpMethod.GET,
                                "/",
                                "/qr/page",
                                "/qr.html",
                                "/favicon.ico",
                                "/css/**", "/js/**", "/images/**",
                                "/error"
                        ).permitAll()

                        //  나머지는 인증 필요
                        .anyRequest().authenticated()
                );

        return http.build();
    }

    //  CORS 설정 (React, Vue 등 외부에서 POST 허용을 위한 설정)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(
                "http://localhost:3000",
                "http://localhost:8080",
                "http://43.200.191.61:8080"
        ));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
