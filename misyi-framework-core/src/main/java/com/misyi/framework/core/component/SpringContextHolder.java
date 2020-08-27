package com.misyi.framework.core.component;

import org.apache.commons.lang3.Validate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Spring上下文持有器
 *
 * @author licong
 * @since 2020/8/7 10:23 上午
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

	private SpringContextHolder() {
	}

	private static ApplicationContext applicationContext = null;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		SpringContextHolder.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		assertContextInjected();
		return applicationContext;
	}

	public static Object getBean(String beanName) {
		return getApplicationContext().getBean(beanName);
	}

	public static <T> T getBean(Class<T> requiredType) {
		return getApplicationContext().getBean(requiredType);
	}

	public static <T> T getBean(String beanName, Class<T> requiredType) {
		return getApplicationContext().getBean(beanName, requiredType);
	}

	public static <T> Map<String, T> getBeansOfType(Class<T> requiredType) {
		return getApplicationContext().getBeansOfType(requiredType);
	}

	public static <T> List<T> getBeans(Class<T> requiredType) {
		Map<String, T> beanMap = getBeansOfType(requiredType);
		if(null == beanMap) {
			return null;
		}
		return new ArrayList<>(beanMap.values());
	}

	public static String getProperty(String key) {
		return getApplicationContext().getEnvironment().getProperty(key);
	}

	private static void assertContextInjected() {
		Validate.validState(applicationContext != null, "ApplicationContext属性未注入, 请在Application中定义SpringContextHolder.", new Object[0]);
	}

	public static void clearHolder() {
		applicationContext = null;
	}

}