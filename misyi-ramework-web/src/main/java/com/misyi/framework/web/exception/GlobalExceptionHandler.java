package com.misyi.framework.web.exception;

import com.misyi.framework.api.ApiResultBean;
import com.misyi.framework.api.SystemCodeEnum;
import com.misyi.framework.core.util.ExceptionMessageUtils;
import com.misyi.framework.core.util.StringUtils;
import com.misyi.framework.web.header.HeaderHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理器
 *
 * @author licong
 * @since 2020-07-23 11:02 上午
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 参数绑定校验
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResultBean methodArgumentNotValidExceptionHandle(MethodArgumentNotValidException e, HttpServletRequest request) {
        String uri = request.getRequestURI();
        String errorMessage = ExceptionMessageUtils.buildAllFieldErrorMessage(e.getBindingResult());
        String message = String.format("参数校验错误, 请求路径: %s, %s", uri, errorMessage);
        log.error(message);
        log.error("MethodArgumentNotValidException", e);
        return ApiResultBean.failure(HeaderHelper.getRequestId(), SystemCodeEnum.CONSTRAINT_VIOLATIONS_EXCEPTION, errorMessage);
    }

    /**
     * 请求方法错误异常处理
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiResultBean httpRequestMethodNotSupportedExceptionHandle(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        String uri = request.getRequestURI();
        String supportedMethods = StringUtils.join(e.getSupportedMethods(), ",");
        String message = String.format("请求方式错误, 请求路径: %s, 应为: %s, 不支持: %s", uri, supportedMethods, e.getMethod());
        log.error(message);
        log.error("HttpRequestMethodNotSupportedException", e);
        return ApiResultBean.failure(HeaderHelper.getRequestId(), SystemCodeEnum.ILLEGAL_REQUEST, message);
    }


    /**
     * 参数错误
     * POST 请求没有请求体错误
     * InvalidFormatException 解析错误
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiResultBean httpMessageNotReadableExceptionHandle(HttpMessageNotReadableException e, HttpServletRequest request) {
        String uri = request.getRequestURI();
        String message = String.format("请求参数错误, 请求路径: %s", uri);
        log.error(message + ", 异常信息: " + e.getMessage());
        log.error("HttpMessageNotReadableException", e);
        String throwMessage = ExceptionMessageUtils.getSimpleMessage(e.getMessage());
        if (e.getCause() != null) {
            throwMessage = ExceptionMessageUtils.getSimpleMessage(e.getCause().getMessage());
        }
        return ApiResultBean.failure(HeaderHelper.getRequestId(), SystemCodeEnum.ILLEGAL_PARAM, throwMessage);
    }


    /**
     * 业务异常处理
     */
    @ExceptionHandler(BusinessException.class)
    public ApiResultBean businessExceptionHandle(BusinessException e) {
        log.error("BusinessException", e);
        return ApiResultBean.failure(HeaderHelper.getRequestId(), e.getCode(), e.getMessage());
    }

    /**
     * 其他异常处理
     */
    @ExceptionHandler(Exception.class)
    public ApiResultBean exceptionHandle(Exception e, HttpServletRequest request) {
        String message = StringUtils.defaultIfBlank(e.getMessage(), SystemCodeEnum.SYSTEM_EXCEPTION.getMessage());
        log.error(String.format("系统错误: 请求路径为%s, 异常信息为 %s", request.getRequestURI(), message));
        log.error("Exception", e);
        return ApiResultBean.failure(HeaderHelper.getRequestId(), SystemCodeEnum.SYSTEM_EXCEPTION.getCode(), message);
    }
}
