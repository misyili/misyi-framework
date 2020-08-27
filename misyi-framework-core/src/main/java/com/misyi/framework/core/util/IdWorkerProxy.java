package com.misyi.framework.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * IdWorker客户端
 *
 * @author licong
 * @since 2020/8/27 5:13 下午
 */
public final class IdWorkerProxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(IdWorkerProxy.class);

    private static final IdWorker ID_WORKER = new IdWorker();

    /**
     * 生成ID
     * @return
     */
    public static Long generateId(){
        try {
            return ID_WORKER.get();
        }catch (Exception e) {
            LOGGER.error("生成ID异常：{}", e.getMessage());
            throw new RuntimeException("生成ID异常：{}" + e.getMessage());
        }
    }

    /**
     * 生成ID，如果有异常的情况下采用UUID兜底
     * @return
     */
    public static String generateIdIfException(){
        try {
            return Long.toString(ID_WORKER.get());
        }catch (Exception e) {
            LOGGER.error("生成ID异常：{}", e.getMessage());
            return UUID.randomUUID().toString().replaceAll("-", "");
        }
    }
}
