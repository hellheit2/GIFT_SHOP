package com.example.giftshop.common.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, 
                         HttpServletResponse response, 
                         AuthenticationException authException) throws IOException, ServletException {
        
        
        //인증되지 않은 사용자 ajax 요청, Unauthorized 에러
        //request Header의 'x-requested-with'가 'XMLHttpRequest'인지 확인
        if("XMLHttpRequest".equals(request.getHeader("x-requested-with"))){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Unauthorized");
        }else{ //나머지 경우 로그인 페이지로 redirect
            response.sendRedirect("/members/login");
        }
        
    }
}

