package com.misyi.framework.core.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.misyi.framework.api.SystemCodeEnum;
import com.misyi.framework.api.SystemException;
import org.springframework.util.Assert;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * 采用Jackson封装的JSON工具类
 *
 * @author wuwen
 * @date 2017/12/05 14:22:42
 */
public class JSONUtils {

    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        //去掉默认的时间戳格式
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //空值不序列化
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //反序列化时，属性不存在的兼容处理
        OBJECT_MAPPER.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //序列化时，日期的统一格式
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        //单引号处理
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        //Java8日期时间类序列化支持
        OBJECT_MAPPER.registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());
    }

    /**
     * 获取对象映射器
     *
     * @return
     */
    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    /**
     * 解析JSON字符串为指定的对象
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        Assert.hasLength(json, "JSON字符串不能为空");
        Assert.notNull(clazz, "转换的类不能为空");
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (Exception e) {
            throw new SystemException(SystemCodeEnum.JSON_PARSE_EXCEPTION.getValue(), e.getMessage());
        }
    }

    /**
     * 解析JSON字符串为指定的泛型对象
     *
     * @param json
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T> T parseObject(String json, TypeReference<T> typeReference) {
        Assert.hasLength(json, "JSON字符串不能为空");
        Assert.notNull(typeReference, "泛型不能为空");
        try {
            return OBJECT_MAPPER.readValue(json, typeReference);
        } catch (Exception e) {
            throw new SystemException(SystemCodeEnum.JSON_PARSE_EXCEPTION.getValue(), e.getMessage());
        }
    }

    /**
     * 把对象转换为JSON字符串
     *
     * @param object
     * @return
     */
    public static String toJSONString(Object object) {
        Assert.notNull(object, "转换的对象不能为空");
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            throw new SystemException(SystemCodeEnum.JSON_PARSE_EXCEPTION.getValue(), e.getMessage());
        }
    }

    /**
     * 把对象转换为JSON字符串，并美化输出
     *
     * @param object
     * @return
     */
    public static String toJSONStringWithPrettyPrinter(Object object) {
        Assert.notNull(object, "转换的对象不能为空");
        try {
            return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (Exception e) {
            throw new SystemException(SystemCodeEnum.JSON_PARSE_EXCEPTION.getValue(), e.getMessage());
        }
    }

    /**
     * 把JSON字符串转换成JsonNode
     * 此方法可能会拿到节点而返回null，调用方需要处理NPE
     *
     * @param json
     * @return
     */
    public static JsonNode toJSONNode(String json) {
        Assert.hasLength(json, "JSON字符串不能为空");
        try {
            return OBJECT_MAPPER.readTree(json);
        } catch (IOException e) {
            throw new SystemException(SystemCodeEnum.JSON_PARSE_EXCEPTION.getValue(), e.getMessage());
        }
    }

    /**
     * 直接获取JSON字符串里面指定属性的字符串值
     * 如果找不到对应的节点返回""
     *
     * @param json
     * @param fieldName
     * @return
     */
    public static String getStringValue(String json, String fieldName) {
        Assert.hasLength(fieldName, "指定JSON字符串中的属性名字不能为空");
        JsonNode jsonNode = toJSONNode(json);
        if (null != jsonNode) {
            JsonNode value = jsonNode.findValue(fieldName);
            if (null != value) {
                return value.asText();
            }
        }
        return "";
    }

    /**
     * 直接获取JSON字符串里面指定属性的整形值
     * 如果找不到对应的节点会返回null
     *
     * @param json
     * @param fieldName
     * @return
     */
    public static Integer getIntegerValue(String json, String fieldName) {
        Assert.hasLength(fieldName, "指定JSON字符串中的属性名字不能为空");
        JsonNode jsonNode = toJSONNode(json);
        if (null != jsonNode) {
            JsonNode value = jsonNode.findValue(fieldName);
            if (null != value) {
                return value.asInt();
            }
        }
        return null;
    }

}
