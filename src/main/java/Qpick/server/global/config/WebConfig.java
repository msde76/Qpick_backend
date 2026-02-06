package Qpick.server.global.config;

import Qpick.server.global.resolver.AuthUserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthUserArgumentResolver authUserArgumentResolver;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 1. "/api/"로 시작하는 모든 요청
                .allowedOriginPatterns("*")   // 2. (중요) 모든 출처(도메인)의 요청을 허용
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS") // 3. 허용할 HTTP 메서드
                .allowedHeaders("*")   // 4. 허용할 HTTP 헤더
                .allowCredentials(true) // 5. (쿠키 등 자격증명은 일단 비허용)
                .maxAge(3600);         // 6. (Preflight 요청 캐시 시간)
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authUserArgumentResolver);
    }
}