package Qpick.server.global.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 1. 헤더에서 토큰 추출
        String token = resolveToken(request);

        // 2. 유효한 토큰인지 확인
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // 3. 토큰이 유효하면 Authentication 객체 생성하여 SecurityContext에 저장
            Long userId = jwtTokenProvider.getUserId(token);

            // 간단하게 ID만 담은 인증 객체 생성 (DB 조회 없이 성능 최적화)
            // 권한이 필요하다면 여기서 DB 조회해서 Role을 넣어줘야 함
            Authentication auth = new UsernamePasswordAuthenticationToken(userId, null, Collections.emptyList());

            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }

    // Request Header에서 토큰 정보 추출 ("Bearer " 제거)
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}