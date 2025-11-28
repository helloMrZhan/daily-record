package com.zjq.dailyrecord.utils;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * ID生成工具类
 * @author zjq
 */
public class IdUtils {

    /**
     *   单例原子计数器（用于单机环境）
     */
    private static final AtomicInteger counter = new AtomicInteger(0);

    /**
     * 生成 UUID（无横线）
     * 示例：a1b2c3d4e5f64789
     *
     * @return String
     */
    public static String generateUUID() {
        return IdUtil.simpleUUID();
    }

    /**
     * 生成时间戳 + 随机数组合的唯一ID
     * 示例：PREFIX1712345678901_8237
     *
     * @param prefix 前缀（可为空）
     * @return String
     */
    public static String generateTimestampWithRandom(String prefix) {
        long timestamp = System.currentTimeMillis();
        // 生成 1000~9999 的随机数
        int random = RandomUtil.randomInt(1000, 9999);
        return (prefix == null ? "" : prefix) + timestamp  + random;
    }

    /**
     * 生成时间戳 + 自增序列的唯一ID（适合单机）
     * 示例：PREFIX_1712345678901_0001
     *
     * @param prefix 前缀（可为空）
     * @return String
     */
    public static String generateTimestampWithSequence(String prefix) {
        long timestamp = System.currentTimeMillis();
        int seq = counter.getAndIncrement() % 10000;
        // 保证不超过 4 位
        return (prefix == null ? "" : prefix) + timestamp + String.format("_%04d", seq);
    }

    /**
     * 生成 Snowflake ID（分布式适用）
     * 注意：需要引入 Hutool 的 IdUtil.snowflake()
     *
     * @param nodeId 节点ID（0-1023）
     * @return Long
     */
    public static Long generateSnowflakeId(long nodeId) {
        return IdUtil.getSnowflake(nodeId,nodeId).nextId();
    }

    /**
     * 快速生成 Snowflake ID（默认节点为 1）
     * @return Long
     */
    public static Long generateSnowflakeId() {
        return IdUtil.getSnowflake(1, 1).nextId();
    }

    /**
     * 生成短UUID（16位）
     * @return String
     */
    public static String generateShortUUID() {
        return IdUtil.fastSimpleUUID();
    }
}
