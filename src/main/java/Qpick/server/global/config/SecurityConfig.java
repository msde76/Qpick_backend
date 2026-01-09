package Qpick.server.global.config;

import Qpick.server.global.jwt.JwtAuthenticationFilter;
import Qpick.server.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // 추가
import org.springframework.security.crypto.password.PasswordEncoder;     // 추가
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider; // 주입

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // 로그인, 회원가입, Swagger는 인증 없이 접근 가능
                        .requestMatchers("/users/login", "/users/signup", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        // 그 외 모든 요청은 인증 필요 (테스트 할 때 불편하면 .anyRequest().permitAll() 로 유지해도 됨)
                        .anyRequest().authenticated()
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                // 👇 필터 추가! (UsernamePassword 필터 앞에 Jwt 필터를 끼워넣음)
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}