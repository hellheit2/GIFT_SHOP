package com.example.giftshop.common.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//로그인 성공시 이전 페이지
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    public CustomLoginSuccessHandler(String defaultTargetUrl) {
        setDefaultTargetUrl(defaultTargetUrl);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        HttpSession session = request.getSession(); //세션

        if (session != null) { //세션이 null이 아닐 경우
            String redirectUrl = (String) session.getAttribute("referer"); //세션에 저장된 이전페이지 정보
            if (redirectUrl != null) { //이전페이지 url 확인
                session.removeAttribute("referer"); //세션에서 해당 url 정보 제거
                getRedirectStrategy().sendRedirect(request, response, redirectUrl); //확인 url로 redirect
            } else { //이전페이지 url 정보가 없을 경우
                super.onAuthenticationSuccess(request, response, authentication);
            }
        } else { //세션이 null
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}