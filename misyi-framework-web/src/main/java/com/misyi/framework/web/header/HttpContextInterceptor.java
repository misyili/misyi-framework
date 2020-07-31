package com.misyi.framework.web.header;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求栈拦截器
 *
 * @author licong
 * @since 2020-06-09 5:37 下午
 */
@Component
public class HttpContextInterceptor extends HandlerInterceptorAdapter {

    @Value("${ates.springboot.cache.requestHeader.enabled:true}")
    private boolean isCacheRequestHeaderEnabled;
    @Value("${ates.springboot.cache.request.enabled:true}")
    private boolean isCacheRequestEnabled;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if(isCacheRequestHeaderEnabled) {
            HttpContextHolder.INSTANCE.putRequestHeader(request);
        }
        if(isCacheRequestEnabled) {
            HttpContextHolder.INSTANCE.putRequest(request);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if(isCacheRequestHeaderEnabled) {
            HttpContextHolder.INSTANCE.removeRequestHeader();
        }
        if(isCacheRequestEnabled) {
            HttpContextHolder.INSTANCE.removeRequest();
        }
    }
}
