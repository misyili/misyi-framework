package com.misyi.framework.web.header;

/**
 * 请求头信息
 *
 * @author licong
 * @since 2020-06-09 5:02 下午
 */
public class Header {

    /**
     * 请求ID
     */
    private String requestId;

    /**
     * 用户token
     */
    private String token;

    /**
     * 用户ID
     */
    private Long customerId;

    /**
     * 语言标志
     */
    private String language;


    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
