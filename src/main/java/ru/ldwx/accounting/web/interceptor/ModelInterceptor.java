package ru.ldwx.accounting.web.interceptor;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import ru.ldwx.accounting.AuthorizedUser;
import ru.ldwx.accounting.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ModelInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && !modelAndView.isEmpty()) {
            AuthorizedUser authorizedUser = SecurityUtil.safeGet();
            if (authorizedUser != null) {
                modelAndView.getModelMap().addAttribute("userTo", authorizedUser.getUserTo());
            }
        }
    }
}
