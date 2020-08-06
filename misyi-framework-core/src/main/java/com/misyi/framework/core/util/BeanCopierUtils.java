package com.misyi.framework.core.util;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Bean拷贝工具类
 * 暂不支持同属性名原始类型与包装类型的拷贝
 *
 * @author licong
 * @since 2020/8/5 3:14 下午
 */
public class BeanCopierUtils {

    private static final Map<String, BeanCopier> BEAN_COPIER_CACHE = new ConcurrentHashMap<>();
    private static final Map<String, ConstructorAccess> CONSTRUCTOR_ACCESS_CACHE = new ConcurrentHashMap<>();

    /**
     * 单个Bean拷贝
     * @param sourceInstance 源实例
     * @param targetClass 目标类
     * @param <T> 目标类型
     * @return
     */
    public static <T> T copy(Object sourceInstance, Class<T> targetClass) {
       return copy(sourceInstance, targetClass, null);
    }

    /**
     * 单个Bean拷贝
     * @param sourceInstance 源实例
     * @param targetClass 目标类
     * @param <T> 目标类型
     * @param converter 同名称不同类型的转换器
     * @return
     */
    public static <T> T copy(Object sourceInstance, Class<T> targetClass, Converter converter) {
        Assert.notNull(sourceInstance, "sourceInstance must not be null");
        Assert.notNull(targetClass, "targetClass must not be null");
        T target;
        try {
            target = targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(String.format("Create new instance of %s failed: %s", targetClass,
                    e.getMessage()));
        }
        copy(sourceInstance, target, converter);
        return target;
    }

    /**
     * Bean列表拷贝
     * @param sourceInstanceList 源实例列表
     * @param targetClass 目标类
     * @param <T> 目标类型
     * @return
     */
    public static <T> List<T> copy(List<?> sourceInstanceList, Class<T> targetClass, boolean needSort) {
        return copy(sourceInstanceList, targetClass, null, needSort);
    }

    /**
     * Bean列表拷贝
     * @param sourceInstanceList 源实例列表
     * @param targetClass 目标类
     * @param <T> 目标类型
     * @param converter 同名称不同类型的转换器
     * @return
     */
    public static <T> List<T> copy(List<?> sourceInstanceList, Class<T> targetClass, Converter converter,
                                   boolean needSort) {
        Assert.notNull(sourceInstanceList, "sourceInstanceList must not be null");
        Assert.notNull(targetClass, "targetClass must not be null");
        Stream<?> stream;
        if (needSort) {
            stream = sourceInstanceList.stream();
        } else {
            stream = sourceInstanceList.parallelStream();
        }
        return stream.map(obj -> {
            T target;
            try {
                target = getConstructorAccess(targetClass).newInstance();
                copy(obj, target, converter);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return target;
        }).collect(Collectors.toList());
    }

    private static void copy(Object source, Object target) {
        copy(source, target, null);
    }

    private static void copy(Object source, Object target, Converter converter) {
        boolean useConverter = null != converter;
        BeanCopier copier = getBeanCopier(source.getClass(), target.getClass(), useConverter);
        copier.copy(source, target, converter);
    }

    private static BeanCopier getBeanCopier(Class sourceClass, Class targetClass, boolean useConverter) {
        String beanKey = generateKey(sourceClass, targetClass);
        BeanCopier copier;
        if (!BEAN_COPIER_CACHE.containsKey(beanKey)) {
            copier = BeanCopier.create(sourceClass, targetClass, useConverter);
            BEAN_COPIER_CACHE.put(beanKey, copier);
        } else {
            copier = BEAN_COPIER_CACHE.get(beanKey);
        }
        return copier;
    }

    private static String generateKey(Class<?> class1, Class<?> class2) {
        return class1.toString() + class2.toString();
    }

    private static <T> ConstructorAccess<T> getConstructorAccess(Class<T> targetClass) {
        ConstructorAccess<T> constructorAccess = CONSTRUCTOR_ACCESS_CACHE.get(targetClass.toString());
        if (constructorAccess != null) {
            return constructorAccess;
        }
        try {
            constructorAccess = ConstructorAccess.get(targetClass);
            constructorAccess.newInstance();
            CONSTRUCTOR_ACCESS_CACHE.put(targetClass.toString(), constructorAccess);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Create new instance of %s failed: %s", targetClass,
                    e.getMessage()));
        }
        return constructorAccess;
    }


}
