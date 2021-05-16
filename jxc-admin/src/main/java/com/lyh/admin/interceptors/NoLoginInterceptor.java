package com.lyh.admin.interceptors;

import com.lyh.admin.pojo.TUser;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 创建拦截器
 */
public class NoLoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        TUser user =(TUser) request.getSession().getAttribute("user");
        /**
         * 用户未登录或session失效
         */
        if (user == null){
            response.sendRedirect("index");
            return false;
        }
        return true;
    }
}
