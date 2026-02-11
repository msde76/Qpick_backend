package Qpick.server.global.resolver;

import Qpick.server.domain.user.domain.entity.User;
import Qpick.server.domain.user.exception.userException;
import Qpick.server.global.annotation.AuthUser;
import Qpick.server.global.error.code.status.ErrorStatus;
import Qpick.server.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 1. 파라미터에 @AuthUser 어노테이션이 붙어 있는지 확인
        boolean hasAuthAnnotation = parameter.hasParameterAnnotation(AuthUser.class);
        // 2. 파라미터 타입이 User 클래스인지 확인
        boolean isUserType = User.class.isAssignableFrom(parameter.getParameterType());

        return hasAuthAnnotation && isUserType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 👇 [중요] 인증 정보가 없으면 null을 리턴하지 말고, 예외를 터뜨려야 합니다!
        if (authentication == null || authentication.getPrincipal() == null || !(authentication.getPrincipal() instanceof CustomUserDetails)) {
            // 여기서 예외를 던지면 "로그인 하세요"라는 401/403 에러가 나갑니다.
            throw new userException(ErrorStatus._UNAUTHORIZED);
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getUser();
    }
}