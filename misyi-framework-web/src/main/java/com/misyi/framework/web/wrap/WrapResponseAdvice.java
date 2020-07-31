package com.misyi.framework.web.wrap;

import java.lang.annotation.*;

/**
 * 包装 Response
 *
 * @author licong
 * @since 2020-07-20 3:30 下午
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WrapResponseAdvice {
}
