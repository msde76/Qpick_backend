package Qpick.server.global.resolver;

import Qpick.server.domain.user.domain.entity.User;
import Qpick.server.global.annotation.AuthUser;
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

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new RuntimeException("인증 정보가 없습니다."); // 적절한 예외 처리 필요
        }

        // CustomUserDetails에서 User 엔티티 추출
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            return ((CustomUserDetails) principal).getUser();
        }

        return null; // 혹은 예외 발생
    }
}