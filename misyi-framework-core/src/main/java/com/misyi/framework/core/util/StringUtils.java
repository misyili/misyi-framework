package com.misyi.framework.core.util;

import java.util.UUID;

/**
 * 字符串工具类
 *
 * @author licong
 * @since 2020-07-23 10:26 上午
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     * 获取uuid
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
