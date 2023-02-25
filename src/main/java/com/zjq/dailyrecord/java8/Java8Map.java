package com.zjq.dailyrecord.java8;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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




}
