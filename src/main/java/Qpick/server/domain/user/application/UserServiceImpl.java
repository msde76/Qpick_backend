package Qpick.server.domain.user.application;

import Qpick.server.domain.user.converter.UserConverter;
import Qpick.server.domain.user.domain.entity.User;
import Qpick.server.domain.user.domain.repository.UserRepository;
import Qpick.server.domain.user.dto.UserRequestDTO;
import Qpick.server.domain.user.dto.UserResponseDTO;
import Qpick.server.domain.user.exception.userException;
import Qpick.server.global.error.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // 보안 적용 시 주석 해제

    @Override
    @Transactional
    public UserResponseDTO.SignupDTO signup(UserRequestDTO.SignupDTO request) {

        // 1. 이메일 중복 검사
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new userException(ErrorStatus.EMAIL_ALREADY_EXIST);
        }

        // 2. 비밀번호 암호화 (구현 완료)
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 3. 저장 (암호화된 비밀번호를 Converter에 전달)
        User newUser = UserConverter.toEntity(request, encodedPassword);
        User savedUser = userRepository.save(newUser);

        // 4. Response DTO 반환
        return UserConverter.toSignupDTO(savedUser);
    }
}
