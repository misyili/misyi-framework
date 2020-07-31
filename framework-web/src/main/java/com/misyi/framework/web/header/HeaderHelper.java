package com.misyi.framework.web.header;

/**
 * HTTP 请求帮助类
 *
 * @author licong
 * @since 2020-06-09 5:43 下午
 */
public class HeaderHelper {

    /**
     * 获取APP请求头
     * @return
     */
    public static Header getHeader(){
        Header header = HttpContextHolder.INSTANCE.getHeader();
        if (header == null) {
            header = new Header();
        }
        return header;
    }

    public static String getRequestId() {
        return getHeader().getRequestId();
    }

    public static String getToken() {
        return getHeader().getToken();
    }

}
