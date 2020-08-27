package com.misyi.framework.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Api 组件自动扫描
 *
 * @author licong
 * @since 2020-08-27 4:49 下午
 */
@Configuration
@ComponentScan(basePackages = "com.misyi.framework.core.component")
public class FrameworkApiAutoConfiguration {
}
