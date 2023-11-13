package org.koreait.commons.interceptors;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class CommonInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        init(request);

        return true;
    }

    public void init(HttpServletRequest request) { // 초기화

        HttpSession session = request.getSession();

        /* PC, Moblie 수동 변경 처리 S */

        String device = request.getParameter("device");
        if (device != null && !device.isBlank()) {
            session.setAttribute("device", device.toLowerCase().equals("mobile")? "mobile":"pc");
        }

        /* PC, Moblie 수동 변경 처리 E */
    }
}
