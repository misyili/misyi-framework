package com.misyi.framework.api;

/**
 * 系统异常
 *
 * @author licong
 * @since 2020-07-23 11:01 上午
 */
public enum SystemCodeEnum implements IBusinessEnum {

    /**
     * 系统响应
     */
    SUCCESS("200", "请求成功"),
    ILLEGAL_IP("1001", "非法IP"),
    ILLEGAL_APP_ID("1002", "非法AppId"),
    ILLEGAL_SIGNATURE("1003", "非法签名"),
    ACCESS_TOKEN_EXPIRED("1004", "AccessToken已过期"),
    ILLEGAL_ACCESS_TOKEN("1005", "非法AccessToken"),
    ILLEGAL_REQUEST("1006","非法请求"),
    REQUEST_TIMEOUT("1007", "请求超时"),
    ILLEGAL_PARAM("1008", "非法参数"),
    ILLEGAL_REQUEST_AGENT("1009", "非法请求来源"),
    NONE_APP_ACCOUNT_DATA("1010", "没有APP账户数据"),
    JSON_PARSE_EXCEPTION("1011", "JSON解析异常"),
    RPC_EXCEPTION("1012", "远程调用服务异常"),
    CONSTRAINT_VIOLATIONS_EXCEPTION("1013", "参数校验异常"),
    DATABASE_UPDATE_FAILURE("1014", "数据库更新失败"),
    HTTP_REQUEST_FAILURE("1015", "Http请求失败"),
    SYSTEM_EXCEPTION("1099", "系统错误"),
    SERVICE_UPGRADE("1100", "服务升级"),
    SERVICE_PROHIBITED("1101", "服务禁止访问"),
    NO_RESULT("1111", "没有结果"),
    DUBBO_REQUEST_FAILURE("1112", "DUBBO请求失败")
    ;

    private final String code;
    private final String message;

    SystemCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 获取code
     *
     * @return 业务code
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * 获取消息
     *
     * @return 业务消息
     */
    @Override
    public String getMessage() {
        return message;
    }
}
