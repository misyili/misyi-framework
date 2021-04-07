package com.misyi.framework.api;



/**
 * 系统异常，这是非受检异常
 *
 * @author licong
 * @date 2021/4/7 4:10 下午
 */
public class SystemException extends RuntimeException {

    private static final long serialVersionUID = -181248499771720338L;
    private String code;
    private String businessMsg;

    public SystemException() {
        super();
    }

    public SystemException(String code, String message) {
        super(message);
        this.code = code;
    }

    public SystemException(String code, String businessMsg, String message) {
        super(message);
        this.code = code;
        this.businessMsg = businessMsg;
    }

    public SystemException(SystemCodeEnum resultCode) {
        super(resultCode.getDesc());
        this.code = resultCode.getValue();
    }

    public SystemException(IEnum<String> codeEnum) {
        super(codeEnum.getDesc());
        this.code = codeEnum.getValue();
    }

    public SystemException(IBusinessEnum<String> businessEnum) {
        super(businessEnum.getDesc());
        this.code = businessEnum.getValue();
        this.businessMsg = businessEnum.getBusinessDesc();
    }

    public SystemException(SystemException resultCode, String message) {
        super(message);
        this.code = resultCode.getCode();
    }

    public SystemException(String code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public SystemException(SystemCodeEnum resultCode, Throwable cause) {
        super(resultCode.getDesc(), cause);
        this.code = resultCode.getValue();
    }

    public SystemException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public SystemException(String code, String businessMsg, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.businessMsg = businessMsg;
    }

    public String getCode() {
        return code;
    }

    public String getBusinessMsg() {
        return businessMsg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SystemException)) {
            return false;
        }
        SystemException that = (SystemException) o;
        return getCode() == that.getCode();
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }
}
