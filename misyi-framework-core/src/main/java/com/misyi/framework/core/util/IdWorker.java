package com.misyi.framework.core.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 分布式序列号生成器，twitter snowflake算法的实现
 *  1位标志位（固定为0） + 41位时间位（当前时间减去固定时间） + 10位应用节点位（创建对象时传入） + 12位序列位（同一毫秒内自增，下一毫秒重置为0） = 64位long类型数据
 *
 * [注]
 * 1. 该工具依赖服务器 IP 作为唯一发号标志, 所以部署时不允许同一服务在同一机器上部署多台, 否则可能导致重复
 * 2. 如果服务器时钟回拨, 也会导致 ID 重复
 *
 * @author licong
 * @since 2020/8/27 4:56 下午
 */
public class IdWorker {
    /**
     * 序列号的开始时间戳，建议固定某个时间。此时间不能变大，因为时间位（41位）是使用的当前时间和此时间之差<br/>
     * 序列号可使用的时间就是此时间加上41位毫秒之后的时间 <br/>
     * 		比如此时间是2018-01-01 00:00:00 000，即：1514736000000<br/>
     * 		41位毫秒是Math.pow(2, 41)，即：2199023255552 得到时间：3713759255552，换算成时间是：2087-09-07<br/>
     * 		15:47:35:552 也就是可以41位毫秒可以使用69年多<br/>
     */
    static final long EPOCH;
    static final long INITWORKERID;

    // 应用节点占10位，即最多支持1023个节点
    final long workerIdBits = 10;

    // 序列占12位，当同一节点需要在同一毫秒内产生序列号时，此序列自增，到下一毫秒时会重置为0
    final long sequenceBits = 12;

    //标志位，始终为0，保证产生的数字是正数
    final long sequenceMask = (1 << sequenceBits) - 1;

    // 应用节点位需要左移的位数，即是序列位数
    final long workerIdLeftShiftBits = sequenceBits;

    // 时间左移的位数，即是应用节点位数+序列位数
    final long timestampLeftShiftBits = workerIdLeftShiftBits + workerIdBits;

    // 应用节点的最大值，初始化此对象时需要判断是否在此范围内
    final long maxWorkerId = 1 << workerIdBits;

    // 应用节点id
    long workerId;

    // 同一毫秒内的序列号
    long sequence;

    // 产生序列号的最后时间，用于判断是否在同一毫秒内产生
    long lastTime;

    static {
        EPOCH = 1288834974657L;
        int randomNum=0;
        String ipLong="";

        try {
            String ipStr = InetAddress.getLocalHost().getHostAddress();
            ipLong = String.valueOf(getIpConvertNum(ipStr));
            //从1到9的int型随数
            randomNum = (int)(1+ Math.random()*(9-1+1));
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        }finally {
            INITWORKERID = new Long(ipLong.substring(ipLong.length() - 2) + randomNum);
        }
    }

    /**
     * 将ip 地址转换成数字
     *
     * @param ipAddress
     *            传入的ip地址
     * @return 转换成数字类型的ip地址
     */
    public static long getIpConvertNum(String ipAddress) {
        String[] ip = ipAddress.split("\\.");
        long a = Integer.parseInt(ip[0]);
        long b = Integer.parseInt(ip[1]);
        long c = Integer.parseInt(ip[2]);
        long d = Integer.parseInt(ip[3]);

        long ipNum = a * 256 * 256 * 256 + b * 256 * 256 + c * 256 + d;
        return ipNum;
    }

    public IdWorker() {
        this.workerId = INITWORKERID;
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(
                    String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
    }

    /**
     * @description 生成序列号，为了保证多线程时对序列（sequence）的操作，此方法保证同步操作
     * @author fuwei.deng
     * @date 2018年1月25日 下午3:45:11
     * @version 1.0.0
     * @return 序列号
     */
    public synchronized long get() {
        long currentMillis = System.currentTimeMillis();

        if (currentMillis < lastTime) {
            throw new IllegalArgumentException(String.format(
                    "Clock moved backwards, Refusing to generate id for %d milliseconds", lastTime - currentMillis));
        }

        if (lastTime == currentMillis) {
            if (0 == (sequence = ++sequence & sequenceMask)) {
                currentMillis = waitUntilNextTime(currentMillis);
            }
        } else {
            sequence = 0;
        }
        lastTime = currentMillis;
        return ((currentMillis - EPOCH) << timestampLeftShiftBits) | (workerId << workerIdLeftShiftBits)
                | sequence;
    }

    public synchronized String get(String prefix) {
        long currentMillis = System.currentTimeMillis();

        if (currentMillis < lastTime) {
            throw new IllegalArgumentException(String.format(
                    "Clock moved backwards, Refusing to generate id for %d milliseconds", lastTime - currentMillis));
        }

        if (lastTime == currentMillis) {
            if (0 == (sequence = ++sequence & sequenceMask)) {
                currentMillis = waitUntilNextTime(currentMillis);
            }
        } else {
            sequence = 0;
        }
        lastTime = currentMillis;
        long id = ((currentMillis - EPOCH) << timestampLeftShiftBits) | (workerId << workerIdLeftShiftBits)
                | sequence;
        return prefix + id;
    }

    /**
     * @description 当产生序列号时间相同并且同一毫秒内的序列已用完，则等待下一毫秒
     * @author fuwei.deng
     * @date 2018年1月25日 下午3:47:13
     * @version 1.0.0
     * @param lastTime
     * @return
     */
    private long waitUntilNextTime(final long lastTime) {
        long time = System.currentTimeMillis();
        while (time <= lastTime) {
            time = System.currentTimeMillis();
        }
        return time;
    }
}
