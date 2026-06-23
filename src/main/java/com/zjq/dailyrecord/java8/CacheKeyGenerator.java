package com.zjq.dailyrecord.java8;

import cn.hutool.crypto.SecureUtil; // 假设你用的是 Hutool
import com.alibaba.fastjson.JSON;

import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class CacheKeyGenerator {

    public String generateCacheKey(Long variableFieldId, Long orgId, List<ModelInputParamBo> inputVariableValue) {
        // ⚠️ 问题代码：直接修改输入 list
//        inputVariableValue.sort(Comparator.comparing(ModelInputParamBo::getVariableFieldNameEn));
//        System.out.println(Thread.currentThread().getName() + " sorted list: " +
//                inputVariableValue.stream()
//                        .map(ModelInputParamBo::getVariableFieldNameEn)
//                        .collect(Collectors.toList()));
//
//        StringBuilder sb = new StringBuilder();
//        for (ModelInputParamBo p : inputVariableValue) {
//            sb.append(p.getVariableFieldNameEn()).append("=").append(p.getVariableFieldInputValue());
//        }
//        String key = SecureUtil.md5(sb.toString());
//        return key;
        TreeMap<String, String> sortedParams = new TreeMap<>();
        for (ModelInputParamBo param : inputVariableValue) {
            sortedParams.put(param.getVariableFieldNameEn(), param.getVariableFieldInputValue());
        }
        System.out.println(Thread.currentThread().getName() + " sorted map: " + sortedParams);
        StringBuilder paramStr = new StringBuilder();
        paramStr.append("INDEX_CACHE:");
        paramStr.append(orgId);
        paramStr.append(":");
        paramStr.append(variableFieldId);
        paramStr.append(":");
        paramStr.append(SecureUtil.md5(JSON.toJSONString(sortedParams)));
        return paramStr.toString();
    }
}