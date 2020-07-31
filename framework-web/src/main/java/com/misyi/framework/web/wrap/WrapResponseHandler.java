package com.misyi.framework.web.wrap;

import com.misyi.framework.api.ApiResultBean;
import com.misyi.framework.web.header.HeaderHelper;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * response 拦截处理
 *
 * @author licong
 * @since 2020-07-20 3:32 下午
 */
@ControllerAdvice
public class WrapResponseHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        if (returnType.hasMethodAnnotation(ExceptionHandler.class)) {
            return false;
        }
        boolean classAnnotation = returnType.getDeclaringClass().isAnnotationPresent(WrapResponseAdvice.class);
        boolean methodAnnotation = returnType.hasMethodAnnotation(WrapResponseAdvice.class);
        return classAnnotation || methodAnnotation;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        return ApiResultBean.success(HeaderHelper.getRequestId(), body);
    }


}
