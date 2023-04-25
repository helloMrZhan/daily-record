package com.zjq.dailyrecord.algorithm.list;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.fastjson.JSON;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: TSSLAdmin
 * @CreateTime: 2023-03-29  20:19
 * @Description: TODO
 * @Version 1.0
 */
public class Test_ArrayTreefy {


    static String [][] strings = {{"A"},{"A","A2","A21"},
            {"A","A3","A31"},{"A","A3"},{},
            {"b","B1","B11"},{"D"},
            {"c","c1","c11"},{"d","d1","d11"}};

    public static void main(String[] args) {
        Map<String, Map<String, List<String[]>>> collect = Arrays.stream(strings).filter(ar->ar != null && ar.length > 0)
                .collect(Collectors.groupingBy(aa -> aa != null && aa.length > 0 ? aa[0]: "",
                        Collectors.groupingBy(bb -> bb != null && bb.length > 1? bb[1]: "", Collectors.toList())));
        List<Node> res = collect.keySet().stream().map(first -> {
            // 最上层
            Node father = Node.builder().code(first).name("银行名称" + first).build();
            Map<String, List<String[]>> sencondMap = collect.get(first);
            if (sencondMap != null) {
                father.setChildren(sencondMap.keySet().stream().filter(second-> StringUtils.isNotBlank(second)).map(second -> {
                    Node son = Node.builder().code(second).name("分支行" + second).build();
                    List<String[]> thirds = sencondMap.get(second);
                    if (CollectionUtil.isNotEmpty(thirds) && thirds.size() > 2) {
                        son.setChildren(thirds.stream()
                                .map(arr -> Node.builder().code(arr[2]).name("支行" + arr[2]).build())
                                .collect(Collectors.toList()));
                    }
                    return son;
                }).collect(Collectors.toList()));
            }
            return father;
        }).collect(Collectors.toList());
        System.out.println(JSON.toJSON(res));
    }
    @Data
    @Builder
    @ToString
    static class Node{
        String code;
        String name;
        List<Node> children;
    }





}
