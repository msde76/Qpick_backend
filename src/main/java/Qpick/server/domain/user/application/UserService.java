package Qpick.server.domain.user.application;

import Qpick.server.domain.user.dto.UserRequestDTO;
import Qpick.server.domain.user.dto.UserResponseDTO;

public interface UserService {

    UserResponseDTO.SignupDTO signup(UserRequestDTO.SignupDTO request);

    UserResponseDTO.LoginDTO login(UserRequestDTO.LoginDTO request);

    UserResponseDTO.UserInfoDTO userInfo(Long userId);
}
