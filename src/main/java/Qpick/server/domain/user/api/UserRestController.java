package Qpick.server.domain.user.api;

import Qpick.server.domain.user.application.UserService;
import Qpick.server.domain.user.dto.UserRequestDTO;
import Qpick.server.domain.user.dto.UserResponseDTO;
import Qpick.server.global.common.response.BaseResponse;
import Qpick.server.global.error.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입 API", description = "새로운 유저를 회원가입하여 생성")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "USER_200", description = "OK, 성공적으로 조회되었습니다.")
    })
    public BaseResponse<UserResponseDTO.SignupDTO> signup(
            @RequestBody @Valid UserRequestDTO.SignupDTO userRequestDTO
    ) {
        UserResponseDTO.SignupDTO result = userService.signup(userRequestDTO);
        return BaseResponse.onSuccess(SuccessStatus.SIGNUP, result);
    }

    @PostMapping("/login")
    @Operation(summary = "로그인 API", description = "로그인")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "USER_200", description = "OK, 성공적으로 조회되었습니다.")
    })
    public BaseResponse<UserResponseDTO.LoginDTO> login(
            @RequestBody @Valid UserRequestDTO.LoginDTO userRequestDTO
    ) {
        UserResponseDTO.LoginDTO result = userService.login(userRequestDTO);
        return BaseResponse.onSuccess(SuccessStatus.LOGIN, result);
    }

    @GetMapping("/me")
    @Operation(summary = "내 정보 조회 API", description = "특정 사용자의 정보를 조회한다")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "USER_200", description = "OK, 성공적으로 조회되었습니다.")
    })
    public BaseResponse<UserResponseDTO.UserInfoDTO> userInfo(
            @AuthenticationPrincipal Long userId
    ) {
        UserResponseDTO.UserInfoDTO result = userService.userInfo(userId);
        return BaseResponse.onSuccess(SuccessStatus.USERINFO, result);
    }
}
