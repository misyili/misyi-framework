package com.misyi.framework.api;

/**
 * 基础枚举接口
 *
 * @author licong
 * @date 2021/4/7 4:12 下午
 */
public interface IEnum<T> extends ValueBean<T> {

    /**
     * 是否匹配
     *
     * @param value
     * @return
     */
    boolean matches(T value);

    /**
     * 是否匹配
     *
     * @param iEnum
     * @return
     */
    boolean matches(IEnum<T> iEnum);
}
