package com.misyi.framework.api;

/**
 * 用于封装单个值的实例
 *
 * @author licong
 * @date 2021/4/7 4:12 下午
 */
public interface ValueBean<T> {

    /**
     * 获取实例的描述
     * @return
     */
    String getDesc();

    /**
     * 获取实例的值
     * @return
     */
    T getValue();

}
