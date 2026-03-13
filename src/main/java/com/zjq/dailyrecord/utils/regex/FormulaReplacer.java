package com.zjq.dailyrecord.utils.regex;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 公式替换工具类
 * @author zjq
 */
public class FormulaReplacer {

    public static void main(String[] args) {
        // 1. 准备数据
        String formula = "[NMO&MS-患者入组总表].FILTER(CurrentValue.[现关爱管理师姓名]=[\u200B#{node_ocmk4v6gsf4}]&&CurrentValue.[脱落时间]>=DATE(\u200B#{node_ocmk4v6gsfu})&&CurrentValue.[脱落时间]<=DATE(\u200B#{node_ocmk4v6gsfu})&&CurrentValue.[病种]=\"\u200B#{node_ocmkdna3702}\").[脱落时间].LISTCOMBINE().COUNTA()";
        String cleanFormula = formula.replace("\u200B", "");

        String fieldListJson = "[\n" +
                "                    {\n" +
                "                        \"key\": \"value004\",\n" +
                "                        \"value\":\"张三\",\n" +
                "                        \"label\": \"关爱管理师姓名\",\n" +
                "                        \"operator\":\"等于\",\n" +
                "                        \"metaKey\": \"node_ocmk4v6gsf4\",\n" +
                "                        \"formId\": \"2013801619569352705\",\n" +
                "                        \"formName\": \"患者入组表单\",\n" +
                "                        \" metaComponent\":\"FormInput\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"key\": \"value003\",\n" +
                "                        \"label\": \"脱落时间\",\n" +
                "                        \"value\":\"2026-01-10\",\n" +
                "                        \"operator\":\"大于等于\",\n" +
                "                        \"metaKey\": \"node_ocmk4v6gsfu\",\n" +
                "                        \"formId\": \"2013801619569352705\",\n" +
                "                        \"formName\": \"患者入组表单\",\n" +
                "                        \" metaComponent\":\"FormDatePicker\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"key\": \"value003\",\n" +
                "                        \"label\": \"脱落时间\",\n" +
                "                        \"value\":\"2026-01-17\",\n" +
                "                        \"operator\":\"小于等于\",\n" +
                "                        \"metaKey\": \"node_ocmk4v6gsfu\",\n" +
                "                        \"formId\": \"2013801619569352705\",\n" +
                "                        \"formName\": \"患者入组表单\",\n" +
                "                        \" metaComponent\":\"FormDatePicker\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"key\": \"value003\",\n" +
                "                        \"label\": \"病种\",\n" +
                "                        \"value\":\"SMA\",\n" +
                "                        \"operator\":\"等于\",\n" +
                "                        \"metaKey\": \"node_ocmkdna3702\",\n" +
                "                        \"formId\": \"2013801619569352705\",\n" +
                "                        \"formName\": \"患者入组表单\",\n" +
                "                        \" metaComponent\":\"FormSelect\"\n" +
                "                    }\n" +
                "                ]";

        JSONArray fieldList = new JSONArray(fieldListJson);
        System.out.println("原始公式: " + cleanFormula);

        // 2. 将 fieldList 转换为 List<Map<String, Object>> 便于处理
        List<JSONObject> fieldObjects = fieldList.toList(JSONObject.class);

        // 重新实现一个更精确的替换方法
        String resultFormula = replaceFormulaPlaceholders(cleanFormula, fieldObjects);
        
        System.out.println("替换后公式: " + resultFormula);
    }

    /**
     * 根据 fieldList 精确替换公式中的占位符
     * @param formula 原始公式
     * @param fieldObjects 字段配置列表
     * @return 替换后的公式
     */
    public static String replaceFormulaPlaceholders(String formula, List<JSONObject> fieldObjects) {
        // 按 metaKey 分组，保持顺序
        Map<String, Deque<JSONObject>> groupedFields = new LinkedHashMap<>();
        for (JSONObject obj : fieldObjects) {
            groupedFields.computeIfAbsent(obj.getStr("metaKey"), k -> new ArrayDeque<>()).addLast(obj);
        }

        String result = formula;
        Pattern pattern = Pattern.compile("#\\{([^}]+)\\}");

        Matcher matcher = pattern.matcher(result);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            String fullMatch = matcher.group(0); // 例如 "#{node_ocmk4v6gsfu}"
            String metaKey = matcher.group(1);   // 例如 "node_ocmk4v6gsfu"

            // 从队列中取出下一个要替换的值
            Deque<JSONObject> queue = groupedFields.get(metaKey);
            if (queue != null && !queue.isEmpty()) {
                JSONObject condition = queue.removeFirst(); // 取出第一个并移除，确保顺序正确
                String replacementValue = condition.getStr("value");
                // 如果值是字符串，可能需要加上引号，具体取决于公式语法
                // 这里假设值是日期或数字，直接替换。如果是字符串，可能需要处理 "SMA" -> "'SMA'"
                 if ("等于".equals(condition.getStr("operator"))) { // 如果是等于操作符且值是字符串，可能需要加引号
                     if (!isNumeric(replacementValue)) {
                         replacementValue = "\"" + replacementValue + "\"";
                     }
                 }
                matcher.appendReplacement(sb, replacementValue);
            } else {
                // 如果找不到对应的值，保留原占位符
                matcher.appendReplacement(sb, fullMatch);
            }
        }
        matcher.appendTail(sb);

        return sb.toString();
    }

    /**
     * 辅助方法：判断字符串是否为数字
     */
    private static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}