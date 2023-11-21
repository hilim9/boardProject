package org.koreait.commons;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class Utils {

    private static ResourceBundle validationsBundle;
    private static ResourceBundle errorsBundle;
    private static ResourceBundle commonsBundle;

    private final HttpServletRequest request;

    private final HttpSession session;


    // 초기화
    static {
        validationsBundle = ResourceBundle.getBundle("messages.validations");
        errorsBundle = ResourceBundle.getBundle("messages.errors");
        commonsBundle = ResourceBundle.getBundle("messages.commons");
    }

    public static String getMessage(String code, String bundleType) {

        bundleType = Objects.requireNonNullElse(bundleType, "validation");
        ResourceBundle bundle = null;

        if (bundleType.equals("common")) {
            bundle = commonsBundle;
        } else if (bundleType.equals("error")) {
            bundle = errorsBundle;
        } else {
           bundle = validationsBundle;
        }

        try {

            return bundle.getString(code);

        } catch (Exception e) {
            return null;
        }
    }
    
    public boolean isMobile() {

        String device = (String) session.getAttribute("device");
        if (device != null) {

            return device.equals("mobile");
        }

        // 요청 헤더 User-Agent 확인 (장비확인)
        // mobile 장비일 때 페이지 변환
        boolean isMobile = request.getHeader("User-Agent").matches(".*(iPhone|iPod|iPad|BlackBerry|Android|Windows CE|LG|MOT|SAMSUNG|SonyEricsson).*");

        return isMobile;
    }

    public String tpl(String tplPath) {

        return String.format("%s/" + tplPath, isMobile() ? "mobile":"front");
    }

    public static void loginInit(HttpSession session) {
        
        // 유효성 값 제거
        session.removeAttribute("email");
        session.removeAttribute("NotBlank_email");
        session.removeAttribute("NotBlank_password");
        session.removeAttribute("globalError");

    }

    /**
     * 단일 요청 데이터 조회
     */
    public String getParam(String name) {
        return request.getParameter(name);
    }

    /**
     * 복수개 요청 데이터 조회
     *
     */
    public String[] getParams(String name) {
        return request.getParameterValues(name);
    }


    public static int getNumber(int num, int defaultValue) {
        return num <= 0 ? defaultValue : num;
    }

    /**
     * 비회원 구분 UID
     * 비회원 구분은 IP + 브라우저 종류
     *
     */
    public int guestUid() {
        String ip = request.getRemoteAddr(); // ip주소
        String ua = request.getHeader("User-Agent"); // 브라우저 정보

        return Objects.hash(ip, ua); // 유일한 값을 만들어서 비회원 처리 (주소가 같지만 브라우저 정보가 다를때는 다른 값으로 만들어짐)
    }

}
