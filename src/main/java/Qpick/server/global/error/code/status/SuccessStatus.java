package Qpick.server.global.error.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {

    // Common
    OK(HttpStatus.OK, "COMMON_200", "성공입니다."),

    // USER
    SIGNUP(HttpStatus.CREATED, "USER_200", "성공적으로 회원가입을 완료했습니다."),
    LOGIN(HttpStatus.OK, "USER_201", "성공적으로 로그인을 완료했습니다."),
    USERINFO(HttpStatus.OK, "USER_202", "성공적으로 유저 정보를 조회했습니다."),

    //Product
    REGISTER(HttpStatus.CREATED, "PRODUCT_200", "성공적으로 상품을 등록했습니다."),
    PRODUCT_INFO(HttpStatus.OK, "PRODUCT_201", "성공적으로 상품을 조회했습니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public ReasonDTO getReasonHttpStatus() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .httpStatus(httpStatus)
                .build();
    }
}