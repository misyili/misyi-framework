package com.misyi.framework.api;

/**
 * 业务枚举接口
 *
 * @author licong
 * @since 2020-07-23 10:49 上午
 */
public interface IBusinessEnum {
    /**
     * 获取code
     * @return 业务code
     */
    String getCode();

    /**
     * 获取消息
     * @return 业务消息
     */
    String getMessage();
}
