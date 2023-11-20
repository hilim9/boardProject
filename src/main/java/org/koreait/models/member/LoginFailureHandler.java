package org.koreait.models.member;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.koreait.commons.Utils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        HttpSession session = request.getSession();

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Utils.loginInit(session);
        
        boolean isRequiredFieldCheck = false;

        session.setAttribute("email", email);

        /* 필수 항목 검증 - email, password S */
        if (email == null || email.isBlank()) {
            session.setAttribute("NotBlank_email", Utils.getMessage("NotBlank.email", "validation"));

            isRequiredFieldCheck = true;
        }

        if (password == null || password.isBlank()) {
            session.setAttribute("NotBlank_password", Utils.getMessage("NotBlank.password", "validation"));

            isRequiredFieldCheck = true;
        }

        /* 필수 항목 검증 - email, password E */

        if (!isRequiredFieldCheck) { // 아이디,비밀번호가 없거나 잘못된 경우
            session.setAttribute("globalError",Utils.getMessage("Login.fail", "validation"));
        }
        
        // 실패시 이동할 페이지
        response.sendRedirect(request.getContextPath() + "/member/login");
    }

}
