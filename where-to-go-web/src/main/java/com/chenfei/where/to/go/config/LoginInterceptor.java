package com.chenfei.where.to.go.config;
/*
 * Created by chenfei on 2019/3/24 19:24
 */

import com.chenfei.where.to.go.enums.BizEnum;
import com.chenfei.where.to.go.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Iterator;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {


    private ExcludeLoginProperties excludeLoginProperties;



    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(httpServletRequest.getServletContext());
        excludeLoginProperties= (ExcludeLoginProperties) applicationContext.getBean("excludeLoginProperties");
        log.info("---------------------开始进入请求地址拦截----------------------------");
        //判断是否是免验证的请求
        if(!checkExcludePath(httpServletRequest)){
            //判断是否登录或者授权
            checkIsLogin(httpServletRequest);
        };
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        httpServletResponse.addHeader("x-token",this.getToken(httpServletRequest));
        log.info("--------------处理请求完成后视图渲染之前的处理操作---------------");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        httpServletResponse.addHeader("x-token",this.getToken(httpServletRequest));
        log.info("---------------视图渲染之后的操作-------------------------"+this.getToken(httpServletRequest));
    }

    boolean checkExcludePath(HttpServletRequest request) {
        String path = excludeLoginProperties.getPath();
        if (StringUtils.isEmpty(path)) {
            throw new CommonException(BizEnum.NO_LOGIN.getCode(),BizEnum.NO_LOGIN.getDesc());
        } else {
            Iterator var2 = Arrays.asList(path.split(",")).iterator();
            String excludePath;
            String uri;
            do {
                if (!var2.hasNext()) {
                    throw new CommonException(BizEnum.NO_LOGIN.getCode(),BizEnum.NO_LOGIN.getDesc());
                }

                excludePath = (String)var2.next();
                uri = request.getRequestURI();
            } while(!uri.contains(excludePath.trim()));
            return true;
        }
    }

    private boolean checkIsLogin(HttpServletRequest httpServletRequest) {
        String token = this.getToken(httpServletRequest);
        String key = excludeLoginProperties.getKey();
        if (StringUtils.isEmpty(token)) {
            throw new CommonException(BizEnum.NO_LOGIN.getCode(),BizEnum.NO_LOGIN.getDesc());
        }
        if(!token.equalsIgnoreCase(key)){
            throw new CommonException(BizEnum.NO_LOGIN.getCode(),BizEnum.NO_LOGIN.getDesc());
        }
        return true;
    }

    private String getToken(HttpServletRequest httpServletRequest) {
        return   httpServletRequest.getHeader("x-token");
    }
}
