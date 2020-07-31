package com.misyi.framework.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 反射工具类
 *
 * @author licong
 * @since 2020/7/20 2:45 下午
 */
public class ReflectionUtils {

    private static final Logger log = LoggerFactory.getLogger(ReflectionUtils.class);

    /**
     * 通过反射设置属性的值
     *
     * @param instance 对象实例
     * @param name 属性名称
     * @param value 属性值
     */
    public static void setPropertyValue(Object instance, String name, String value) {
        try {
            Class<?> clazz = instance.getClass();
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            Class<?> fieldType = field.getType();
            String typeName = fieldType.getCanonicalName();
            if ("java.lang.Long".equals(typeName) || "long".equals(typeName)) {
                field.set(instance, Long.parseLong(value));
                return;
            }
            if ("java.lang.Integer".equals(typeName) || "int".equals(typeName)) {
                field.set(instance, Integer.parseInt(value));
            } else {
                field.set(instance, value);
            }
        } catch (NoSuchFieldException e) {
            log.debug("设置HTTP请求头的属性{}，值{}失败：不存在该属性", name, value);
        } catch (Exception e) {
            log.debug("设置HTTP请求头的属性{}，值{}失败：{}", name, value, e.getMessage());
        }
    }

    /**
     * 把Bean转换成Map用于签名
     *
     * @param instance 待转换的对象
     * @return 转换后的 map 对象
     */
    public static Map<String, Object> convertBean2MapForSignature(Object instance) {
        Map<String, Object> parameters = new HashMap<>();
        try {
            PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(instance.getClass()).getPropertyDescriptors();
            String property;
            Object value;
            for (PropertyDescriptor pd : propertyDescriptors) {
                property = pd.getName();
                if ("sign".equals(property) || "class".equals(property) || "serialVersionUID".equals(property)) {
                    continue;
                }
                value = pd.getReadMethod().invoke(instance);
                if (null == value || "".equals(value)) {
                    continue;
                }
                parameters.put(property, value);
            }
        } catch (Exception e) {
            throw new RuntimeException("转换Bean出现异常：{}" + e.getMessage());
        }
        return parameters;
    }

    /**
     * 把带"-"的属性转成驼峰
     *
     * @param originalName 属性名
     * @return 返回驼峰格式的属性名
     */
    public static String convertDelimited2Hump(String originalName, String regex) {
        if (originalName.contains(regex)) {
            String[] names = originalName.toLowerCase().split(regex);
            List<String> nameList = Arrays.stream(names)
                    .filter(StringUtils::isNotBlank)
                    .collect(Collectors.toList());
            StringBuilder builder = new StringBuilder(nameList.get(0));
            nameList.stream().skip(1)
                    .forEach(name ->
                            builder.append(name.substring(0, 1).toUpperCase())
                                    .append(name.substring(1))
                    );
            return builder.toString();

        }
        return originalName.toLowerCase();
    }
}
