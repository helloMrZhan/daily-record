package com.zjq.dailyrecord.java8;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author zjq
 * @date 2023/2/25 10:55
 * <p>title: Java8操作Map集合操作</p>
 * <p>description:</p>
 */
@Slf4j
public class Java8Map {

    public static HashMap getMapData(){
        HashMap<String,Object> mapData = new HashMap(16);
        mapData.put("name","共饮一杯无");
        mapData.put("hobby",new ArrayList<>());
        mapData.put("city",null);
        mapData.put("age",18);
        return mapData;
    }

    @Test
    public void removeDataEmpty(){
        HashMap<String,Object> mapData = getMapData();
        log.info("移除空数据前：{}",mapData);
        Map<String, Object> afterRemoveEmpty = mapData.entrySet().stream()
                .filter(entry -> entry.getValue() != null)
                .filter(entry -> !(entry.getValue() instanceof String) || !StringUtils.isEmpty(entry.getValue()))
                .filter(entry -> !(entry.getValue() instanceof List) || !CollectionUtils.isEmpty((List) entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        log.info("移除空数据前：{}",afterRemoveEmpty);
    }

    /**
     * 并发测试：验证 CacheKeyGenerator 在多线程环境下生成缓存 key 的线程安全性。
     * 通过多个线程共享同一个未排序列表，观察生成的 cache key 是否一致，
     * 从而判断排序逻辑是否存在并发干扰问题。
     *
     * @throws InterruptedException 等待线程池关闭时可能抛出的中断异常
     */
    @Test
    public void ConcurrentTest() throws InterruptedException {
        // 创建一个共享的、未排序的 list（故意乱序）
        List<ModelInputParamBo> sharedList = Arrays.asList(
                new ModelInputParamBo("z_field", "valueZ"),
                new ModelInputParamBo("a_field", "valueA"),
                new ModelInputParamBo("m_field", "valueM")
        );

        CacheKeyGenerator generator = new CacheKeyGenerator();

        int threadCount = 100;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        // 线程安全的 set
        Set<String> results = ConcurrentHashMap.newKeySet();
        List<Throwable> exceptions = Collections.synchronizedList(new ArrayList<>());

        // 提交多个任务
        // 模拟多线程并发执行排序
        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    // 所有线程共享同一个 sharedList 实例！
                    String key = generator.generateCacheKey(123L, 456L, sharedList);
                    results.add(key);
                } catch (Throwable t) {
                    exceptions.add(t);
                    t.printStackTrace();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        // 输出结果
        System.out.println("共生成 " + results.size() + " 个不同的 cache key:");
        results.forEach(System.out::println);

        if (!exceptions.isEmpty()) {
            System.err.println("\n⚠️ 捕获到 " + exceptions.size() + " 个异常！");
        } else {
            System.out.println("\n✅ 未抛出异常");
        }

        // 预期：如果线程安全，应该只有 1 个唯一 key
        if (results.size() > 1) {
            System.err.println("\n❌ 发现多个不同 key！说明存在并发问题（排序被干扰）");
        } else {
            System.out.println("\n✅ 所有线程生成相同 key（本次运行未发现问题）");
        }
    }




}
