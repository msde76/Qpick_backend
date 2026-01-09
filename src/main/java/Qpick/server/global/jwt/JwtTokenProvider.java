package Qpick.server.global.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-token-validity-in-seconds}")
    private long accessTokenValidityTime;

    @Value("${jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenValidityTime;

    private Key key;

    // 객체 초기화 시, 비밀키를 Base64로 디코딩하여 Key 객체에 저장
    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // 1. Access Token 생성
    public String createAccessToken(Long userId, String email) {
        return createToken(userId, email, accessTokenValidityTime);
    }

    // 2. Refresh Token 생성
    public String createRefreshToken(Long userId) {
        // Refresh Token에는 민감한 정보(이메일 등)를 최소화
        return createToken(userId, null, refreshTokenValidityTime);
    }

    // 내부 토큰 생성 메서드
    private String createToken(Long userId, String email, long validityTime) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(userId));
        if (email != null) {
            claims.put("email", email);
        }

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityTime * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 3. 토큰에서 User ID 추출
    public Long getUserId(String token) {
        return Long.parseLong(parseClaims(token).getSubject());
    }

    // 4. 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}