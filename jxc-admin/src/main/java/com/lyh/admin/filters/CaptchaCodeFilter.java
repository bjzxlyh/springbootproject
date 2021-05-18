package com.lyh.admin.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyh.admin.model.CaptchaImageModel;
import com.lyh.admin.model.RespBean;
import com.mysql.cj.x.protobuf.MysqlxExpr;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

@Component
public class CaptchaCodeFilter extends OncePerRequestFilter {
    private static ObjectMapper objectMapper = new ObjectMapper();
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
     //只有在登录请求时才有验证码过滤操作
        if (StringUtils.equals("/login",httpServletRequest.getRequestURI()) && StringUtils.equalsIgnoreCase(httpServletRequest.getMethod(),"post")){
            //校验登录验证码是否正确
            try {
                this.validate(new ServletWebRequest(httpServletRequest));
            } catch (AuthenticationException e) {
                httpServletResponse.setContentType("application/json;charset=UTF-8");
                httpServletResponse.getWriter().write(objectMapper.writeValueAsString(
                        RespBean.error("验证码错误！")));
                return;
            }
        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    private void validate(ServletWebRequest servletWebRequest) throws ServletRequestBindingException {
        //拿到session
        HttpSession session = servletWebRequest.getRequest().getSession();
        //获取请求中的验证码
        String codeInRequest = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(), "captchaCode");
       //判断请求中是否为空
        if (StringUtils.isEmpty(codeInRequest)){
            throw new SessionAuthenticationException("验证码不能为空!");
        }

        //拿到会话中的值
        CaptchaImageModel codeInSession = (CaptchaImageModel) session.getAttribute("captcha_key");

        //判断会话图片有没有过期
        if (Objects.isNull(codeInSession)){
            throw new SessionAuthenticationException("验证码不存在!");
        }

        if (codeInSession.isExpired()){
            throw new SessionAuthenticationException("验证码已过期！");
        }

        if (!StringUtils.equals(codeInSession.getCode(),codeInRequest)){
            throw new SessionAuthenticationException("验证码不匹配！");
        }
    }


}
