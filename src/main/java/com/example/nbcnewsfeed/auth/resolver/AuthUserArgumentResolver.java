package com.example.nbcnewsfeed.auth.resolver;

import com.example.nbcnewsfeed.auth.dto.AuthUser;
import com.example.nbcnewsfeed.auth.enums.Auth;
import com.example.nbcnewsfeed.auth.dto.LoginRequestDto;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
public class AuthUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        boolean hasAuthAnnotation = parameter.getParameterAnnotation(Auth.class) != null;

        boolean isAuthUserType = parameter.getParameterType().equals(LoginRequestDto.class);

        if (hasAuthAnnotation != isAuthUserType){
            throw new IllegalArgumentException("@Auth와 AuthUser 타입은 함께 사용되어야 합니다.");
        }

        return hasAuthAnnotation;
    }

    @Override
    public Object resolveArgument(
            @Nullable MethodParameter parameter,
            @Nullable ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            @Nullable WebDataBinderFactory binderFactory
    ){

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        Long userId = (Long) request.getAttribute("userId");
        String email = (String) request.getAttribute("email");
        String password = (String) request.getAttribute("password");

        log.info("AuthUserArgumentResolver email={}, password={}",email,password);
//        Authority authority = (Authority) request.getAttribute("authority");
//        return new AuthUserRequestDto(email,password,authority);
        return new AuthUser(userId,email);
    }
}
