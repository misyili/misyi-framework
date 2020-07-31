package com.misyi.framework.core.util;

import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 异常消息处理工具
 *
 * @author licong
 * @since 2020-07-30 11:26 上午
 */
public class ExceptionMessageUtils {

    private ExceptionMessageUtils() {
    }

    /**
     * 组装校验异常信息字段
     * @param bindingResult 绑定结果对象
     * @return 返回字段+默认信息拼接后, 以逗号拼接的字符串
     */
    public static String buildAllFieldErrorMessage(BindingResult bindingResult) {
        List<String> fieldErrors = bindingResult.getFieldErrors().stream()
                .map(item -> item.getField() + item.getDefaultMessage()).collect(Collectors.toList());
        return StringUtils.join(fieldErrors, ",");
    }

    /**
     * 获取简单的异常消息, 从第一个:截取消息
     * @param message 系统抛出的异常信息
     * @return 截取第一个:之前的信息
     */
    public static String getSimpleMessage(String message) {
        if (StringUtils.isBlank(message)) {
            return message;
        }
        return message.substring(0, message.indexOf(":"));
    }
}
