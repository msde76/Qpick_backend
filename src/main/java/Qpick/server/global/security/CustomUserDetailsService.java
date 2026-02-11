package Qpick.server.global.security;

import Qpick.server.domain.user.domain.entity.User;
import Qpick.server.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // 1. 로그인할 때 쓰는 메서드 (기존에 있을 수 있음)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username) // 혹은 findByUsername
                .orElseThrow(() -> new UsernameNotFoundException("해당 이메일의 유저가 없습니다."));
        return new CustomUserDetails(user);
    }

    // 👇 2. [추가] 토큰 필터에서 ID로 유저를 찾을 때 쓰는 메서드
    public UserDetails loadUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 ID의 유저가 없습니다."));

        // 여기서 방금 만드신 CustomUserDetails를 생성해서 반환합니다!
        return new CustomUserDetails(user);
    }
}