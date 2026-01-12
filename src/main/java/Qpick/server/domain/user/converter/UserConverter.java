package Qpick.server.domain.user.converter;

import Qpick.server.domain.user.domain.entity.User;
import Qpick.server.domain.user.dto.UserRequestDTO;
import Qpick.server.domain.user.dto.UserResponseDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserConverter {

    public static User toEntity(UserRequestDTO.SignupDTO request, String encodedPassword) {
        return User.builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .nickname(request.getNickname())
                .build();
    }

    public static UserResponseDTO.SignupDTO toSignupDTO(User user) {
        return UserResponseDTO.SignupDTO.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .createdAt(LocalDateTime.now()) // 혹은 user.getCreatedAt() (JPA Auditing 적용 시)
                .build();
    }

    public static UserResponseDTO.LoginDTO toLoginDTO(User user, String accessToken, String refreshToken) {
        return UserResponseDTO.LoginDTO.builder()
                .userId(user.getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public static UserResponseDTO.UserInfoDTO toUserInfoDTO(User user) {
        return UserResponseDTO.UserInfoDTO.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .point(user.getPoint())
                .build();
    }
}
