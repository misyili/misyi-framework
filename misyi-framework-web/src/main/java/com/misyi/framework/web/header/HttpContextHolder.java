package com.misyi.framework.web.header;


import com.misyi.framework.core.util.ReflectionUtils;
import com.misyi.framework.core.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * HTTP 上下文持有者
 *
 * @author licong
 * @since 2020-06-09 4:49 下午
 */
public enum HttpContextHolder {

    /**
     * 枚举单例
     */
    INSTANCE;

    /**
     * 请求线程变量
     */
    private final static ThreadLocal<HttpServletRequest> REQUEST_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 指定的APP请求头
     */
    private final static ThreadLocal<Header> REQUEST_HEADER = new ThreadLocal<>();


    /**
     * 新增请求
     */
    public final void putRequest(HttpServletRequest request) {
        REQUEST_THREAD_LOCAL.set(request);
    }

    /**
     * 移除请求
     */
    public final void removeRequest() {
        REQUEST_THREAD_LOCAL.remove();
    }

    /**
     * 获取当前线程请求
     */
    public final HttpServletRequest getRequest() {
        return REQUEST_THREAD_LOCAL.get();
    }


    /**
     * 获取请求头
     */
    public final Header getHeader() {
        return REQUEST_HEADER.get();
    }

    /**
     * 新增APP请求头
     */
    public final void putRequestHeader(HttpServletRequest request) {
        Header header = new Header();
        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String name = headers.nextElement();
            String value = request.getHeader(name);
            String humpName = ReflectionUtils.convertDelimited2Hump(name, "-");
            ReflectionUtils.setPropertyValue(header, humpName, value);
        }
        header.setRequestId(StringUtils.uuid());
        REQUEST_HEADER.set(header);
    }

    /**
     * 直接添加请求头
     */
    public final void putHeader(Header header) {
        REQUEST_HEADER.set(header);
    }

    /**
     * 移除HTTP请求头
     */
    public final void removeRequestHeader() {
        REQUEST_HEADER.remove();
    }
}
