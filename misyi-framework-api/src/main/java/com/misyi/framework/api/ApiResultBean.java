package com.misyi.framework.api;


import java.io.Serializable;

/**
 * API统一响应报文结构
 *
 * @author licong
 * @since 2020-07-24 11:03 上午
 */
public class ApiResultBean implements Serializable {

    /**
     * 全局统一请求ID
     */
    private String requestId;

    /**
     * 结果编码
     */
    private String code;

    /**
     * 结果消息
     */
    private String message;

    /**
     * 结果数据
     */
    private Object data;

    /**
     * 系统时间戳
     */
    private Long timestamp;

    public ApiResultBean(String requestId, String code, String message, Object data) {
        this.requestId = requestId;
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    public ApiResultBean(String requestId, String code, String message) {
        this.requestId = requestId;
        this.code = code;
        this.message = message;
        this.data = null;
        this.timestamp = System.currentTimeMillis();
    }

    public static ApiResultBean success(String requestId, Object data) {
        return new ApiResultBean(requestId, SystemCodeEnum.SUCCESS.getCode(), SystemCodeEnum.SUCCESS.getMessage(), data);
    }

    public static ApiResultBean failure(String requestId, String code, String message) {
        return new ApiResultBean(requestId, code, message);
    }

    public static ApiResultBean failure(String requestId, IBusinessEnum businessEnum) {
        return new ApiResultBean(requestId, businessEnum.getCode(), businessEnum.getMessage());
    }

    public static ApiResultBean failure(String requestId, IBusinessEnum businessEnum, String message) {
        return new ApiResultBean(requestId, businessEnum.getCode(), message);
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ApiResultBean{" +
                "requestId='" + requestId + '\'' +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", timestamp=" + timestamp +
                '}';
    }
}
