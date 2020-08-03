package com.misyi.framework.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author licong
 * @since 2020-07-31 9:01 下午
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    private DateUtils() {
    }

    public static final String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String MILLISECONDS_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final String NON_SPLIT_TIME_PATTERN = "yyyyMMddHHmmss";

    public static final String DATE_PATTERN = "yyyy-MM-dd";

    public static final String NON_SPLIT_DATE_PATTERN = "yyyyMMdd";

    public static final String NON_SPLIT_MILLISECONDS_PATTERN = "yyyyMMddHHmmssSSS";

    /**
     * 时间戳转换为日期, 为空则返回null
     *
     * @param timestamp 时间戳
     * @return 日期
     */
    public static Date toDate(Long timestamp) {
        if (timestamp != null && timestamp > 0) {
            return new Date(timestamp);
        }
        return null;
    }


    /**
     * 格式化日期: yyyy-MM-dd
     * @param date 需格式化的日期
     * @return 格式后的字符串
     */
    public static String formatDate(Date date) {
        return format(date, DATE_PATTERN);
    }

    /**
     * 格式化日期: yyyy-MM-dd HH:mm:ss
     * @param date 需格式化的日期
     * @return 格式后的字符串
     */
    public static String formatTime(Date date) {
        return format(date, TIME_PATTERN);
    }


    /**
     * 按指定格式, 格式化日期
     * @param date 需格式化的日期
     * @param pattern 格式
     * @return 格式后的字符串
     */
    public static String format(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }


    /**
     * 解析日期: yyyy-MM-dd HH:mm:ss
     * @param dateStr 需格式化的时间字符串
     * @return 转换后的日期
     */
    public static Date parseTime(String dateStr) {
        return parse(dateStr, TIME_PATTERN);
    }

    /**
     * 解析日期: yyyy-MM-dd
     * @param dateStr 需格式化的时间字符串
     * @return 转换后的日期
     */
    public static Date parseDate(String dateStr) {
        return parse(dateStr, DATE_PATTERN);
    }


    /**
     * 按指定格式, 解析日期
     * @param dateStr 需格式化的时间字符串
     * @param pattern 格式
     * @return 转换后的日期
     */
    public static Date parse(String dateStr, String pattern) {
        try {
            return parseDate(dateStr, pattern);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    /**
     * 获取日期的最后时间
     * 2020-08-03 16:09:17  ->  2020-08-03 23:59:59.999
     * @param date 指定日期
     * @return 指定日期最后时刻
     */
    public static Date getEndTime(Date date) {
        String dateStr = formatDate(date);
        return parse(dateStr + " 23:59:59.999", MILLISECONDS_PATTERN);
    }

    /**
     * 获取日期的初始时间
     * 2020-08-03 16:09:17  ->  2020-08-03 00:00:00
     * @param date 指定日期
     * @return 指定日期最后时刻
     */
    public static Date getBeginTime(Date date) {
        String dateStr = formatDate(date);
        return parse(dateStr + " 00:00:00.000", MILLISECONDS_PATTERN);
    }
}
