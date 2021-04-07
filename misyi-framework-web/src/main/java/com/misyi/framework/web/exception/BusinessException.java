package com.misyi.framework.web.exception;


import com.misyi.framework.api.IEnum;

/**
 * 业务异常
 *
 * @author licong
 * @since 2020-07-23 10:48 上午
 */
public class BusinessException extends RuntimeException {

    private final String code;

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(IEnum<String> businessEnum) {
        super(businessEnum.getDesc());
        this.code = businessEnum.getValue();
    }


    public String getCode() {
        return code;
    }
}
