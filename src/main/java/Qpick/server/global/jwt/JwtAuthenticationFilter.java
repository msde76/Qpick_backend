package Qpick.server.global.jwt;

import Qpick.server.global.security.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService; // 👈 추가! (유저 조회용)

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 1. 헤더에서 토큰 추출
        String token = resolveToken(request);

        // 2. 유효한 토큰인지 확인
        if (token != null && jwtTokenProvider.validateToken(token)) {

            // 3. 토큰에서 유저 ID(혹은 이메일) 추출
            Long userId = jwtTokenProvider.getUserId(token); // 토큰 메서드에 따라 수정 필요 (String email일 수도 있음)

            // 4. ✨ [핵심 수정] DB에서 유저 정보를 가져와서 UserDetails로 만듦
            // (loadUserByUsername 메서드가 ID를 받도록 되어 있다면 사용, 아니면 레포지토리 직접 사용)
            UserDetails userDetails = customUserDetailsService.loadUserById(userId);

            if (userDetails != null) {
                // 5. UserDetails(유저 객체 포함)를 Authentication에 담음
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, // 👈 여기에 userId(숫자) 대신 userDetails(객체)를 넣어야 함!
                        null,
                        userDetails.getAuthorities()
                );

                // 6. 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}