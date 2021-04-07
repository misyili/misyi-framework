package com.misyi.framework.api;


/**
 * 业务枚举类
 *
 * @author licong
 * @date 2021/4/7 4:13 下午
 */
public interface IBusinessEnum<T> extends IEnum<T> {

    /**
     * 获取业务描述
     *
     * @return
     */
    String getBusinessDesc();

}
