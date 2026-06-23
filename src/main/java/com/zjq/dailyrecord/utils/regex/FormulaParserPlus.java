package com.zjq.dailyrecord.utils.regex;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjusters;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

// 假设的 MetaDataItemDefault 类，增加了 defaultValue 字段
class MetaDataItemDefault {
    private String operatorLabel;
    private String placeholderKey;
    private String metaValue;
    private String label;
    private String metaComponent;
    private String defaultValue; // 新增字段

    public MetaDataItemDefault(String operatorLabel, String placeholderKey, String metaValue, String label, String metaComponent, String defaultValue) {
        this.operatorLabel = operatorLabel;
        this.placeholderKey = placeholderKey;
        this.metaValue = metaValue;
        this.label = label;
        this.metaComponent = metaComponent;
        this.defaultValue = defaultValue;
    }

    // Getters...
    public String getDefaultValue() { return defaultValue; }
    public String getOperatorLabel() { return operatorLabel; }
    public String getPlaceholderKey() { return placeholderKey; }
    public String getMetaValue() { return metaValue; }
    public String getLabel() { return label; }
    public String getMetaComponent() { return metaComponent; }

    @Override
    public String toString() {
        return "MetaDataItemDefault{" +
                "operatorLabel='" + operatorLabel + '\'' +
                ", placeholderKey='" + placeholderKey + '\'' +
                ", metaValue='" + metaValue + '\'' +
                ", label='" + label + '\'' +
                ", metaComponent='" + metaComponent + '\'' +
                ", defaultValue='" + defaultValue + '\'' +
                '}';
    }
}

// 为了演示，这里定义一个假的JSONArray类
class JSONArray {}

public class FormulaParserPlus {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 根据函数名和参数计算默认值
     */
    private static String calculateDefaultDate(String function, String argument) {
        LocalDate now = LocalDate.now();
        switch (function.toLowerCase()) {
            case "min":
                switch (argument.toLowerCase()) {
                    case "本周":
                        LocalDate startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                        return startOfWeek.format(FORMATTER);
                    case "本月":
                        LocalDate firstDayOfMonth = now.with(TemporalAdjusters.firstDayOfMonth());
                        return firstDayOfMonth.format(FORMATTER);
                    case "本年":
                        LocalDate firstDayOfYear = now.with(TemporalAdjusters.firstDayOfYear());
                        return firstDayOfYear.format(FORMATTER);
                }
                break;
            case "max":
                switch (argument.toLowerCase()) {
                    case "本周":
                        LocalDate endOfWeek = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
                        return endOfWeek.format(FORMATTER);
                    case "本月":
                        LocalDate lastDayOfMonth = now.with(TemporalAdjusters.lastDayOfMonth());
                        return lastDayOfMonth.format(FORMATTER);
                    case "本年":
                        LocalDate lastDayOfYear = now.with(TemporalAdjusters.lastDayOfYear());
                        return lastDayOfYear.format(FORMATTER);
                }
                break;
        }
        // 如果函数或参数不匹配，返回null或抛出异常
        return null;
    }

    public static class ParseResult {
        private final List<MetaDataItemDefault> items;
        private final String processedFormula;

        public ParseResult(List<MetaDataItemDefault> items, String processedFormula) {
            this.items = items;
            this.processedFormula = processedFormula;
        }

        public List<MetaDataItemDefault> getItems() { return items; }
        public String getProcessedFormula() { return processedFormula; }
    }

    public static ParseResult parseAndReplace(String originalFormula, Map<String, String> valueMap, Map<String, String> metaComponentMap, Map<String, Object> initValueMap, Map<String, JSONArray> operatorsMap) {
        List<MetaDataItemDefault> items = new ArrayList<>();

        // 1. 预处理：找出所有 #{key}=function(...) 模式，并替换为临时占位符
        String tempFormula = originalFormula;
        Map<String, String> placeholderMap = new HashMap<>();
        int placeholderCounter = 0;

        String defaultValuePattern = "(#\\{[^}]+\\}\\s*=\\s*[a-z]+\\(\\s*[^)]+\\s*\\))";
        Pattern defPattern = Pattern.compile(defaultValuePattern, Pattern.CASE_INSENSITIVE);
        Matcher defMatcher = defPattern.matcher(tempFormula);

        StringBuffer sb = new StringBuffer();
        while (defMatcher.find()) {
            String fullMatch = defMatcher.group(1);
            String placeholderKey = "PLACEHOLDER_" + placeholderCounter++;
            placeholderMap.put(placeholderKey, fullMatch);
            defMatcher.appendReplacement(sb, "#{" + placeholderKey + "}");
        }
        defMatcher.appendTail(sb);
        tempFormula = sb.toString();

        // 2. 找到所有 CurrentValue.[label] op [DATE(]#{key} 的位置
        String findPattern = "CurrentValue\\.\\[([\\u4e00-\\u9fa5a-zA-Z0-9_]+)\\](.*?)(?:DATE\\(\\s*)?#\\{([^}]+)\\}";
        Pattern findPat = Pattern.compile(findPattern, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher findMat = findPat.matcher(tempFormula);

        while (findMat.find()) {
            String label = findMat.group(1); // [ ]内的字段名
            String contextBeforeKey = findMat.group(2); // [label] 和 #{key} 之间的内容
            String placeholderKey = findMat.group(3); // #{ }内的key

            // 从 context 中提取 operator
            String operator = extractOperator(contextBeforeKey);

            String defaultValue = null;
            String actualKey = placeholderKey;

            // 3. 检查这个 placeholderKey 是否是我们的临时占位符
            if (placeholderMap.containsKey(placeholderKey)) {
                String originalPattern = placeholderMap.get(placeholderKey);
                // 从原始模式中解析出真实的 key 和默认值
                Matcher originalMatcher = Pattern.compile("#\\{([^}]+)\\}\\s*=\\s*([a-z]+)\\(\\s*([^)]+)\\s*\\)").matcher(originalPattern);
                if (originalMatcher.find()) {
                    actualKey = originalMatcher.group(1); // 真实的 key
                    String funcName = originalMatcher.group(2);
                    String funcArg = originalMatcher.group(3);
                    defaultValue = calculateDefaultDate(funcName, funcArg);
                }
            }

            String operatorLabel = convertOperatorToLabel(operator);
            String metaValue = String.valueOf(valueMap.getOrDefault(actualKey, actualKey));
            String metaComponent = metaComponentMap.get(actualKey);

            items.add(new MetaDataItemDefault(operatorLabel, actualKey, metaValue, label, metaComponent, defaultValue));
        }

        // 4. 清理公式
        String cleanedFormula = originalFormula.replaceAll("\\s*=\\s*[a-z]+\\(\\s*[^)]+\\s*\\)", "");

        return new ParseResult(items, cleanedFormula);
    }

    /**
     * 从操作符和值之间的字符串中提取操作符
     * 使用正则表达式查找操作符
     */
    private static String extractOperator(String context) {
        // 查找 >=, <=, !=, =, >, <，并返回第一个匹配到的
        Pattern opPattern = Pattern.compile("(>=|<=|!=|>|<|=)");
        Matcher opMatcher = opPattern.matcher(context);
        if (opMatcher.find()) {
            return opMatcher.group(1);
        }

        return ""; // 如果没找到，返回空
    }


    private static String convertOperatorToLabel(String op) {
        switch (op) {
            case "=": return "等于";
            case ">=": return "大于等于";
            case "<=": return "小于等于";
            case ">": return "大于";
            case "<": return "小于";
            case "!=": return "不等于";
            default: return op; // 如果遇到未知操作符，直接返回
        }
    }

    public static void main(String[] args) {
        // 测试公式，包含了带默认值的日期字段和普通的文本/状态字段
        String formula = "[SMA-患者入组总表].FILTER(CurrentValue.[现医助姓名]='#{node_ocmkneyhrha}'&&CurrentValue.[入组日期]>=DATE(#{node_ocmkneyhrhe}=min(本周))&&CurrentValue.[入组日期]<=DATE(#{node_ocmkneyhrhe}=max(本周))&&CurrentValue.[患者状态]!='#{node_ocmkneylqh8}')";

        Map<String, String> valueMap = new HashMap<>();
        valueMap.put("node_ocmkneyhrha", "张三");
        valueMap.put("node_ocmkneyhrhe", "2023-01-01");
        valueMap.put("node_ocmkneylqh8", "无效");

        Map<String, String> metaComponentMap = new HashMap<>();
        metaComponentMap.put("node_ocmkneyhrha", "text");
        metaComponentMap.put("node_ocmkneyhrhe", "date");
        metaComponentMap.put("node_ocmkneylqh8", "status");

        ParseResult result = parseAndReplace(formula, valueMap, metaComponentMap, new HashMap<>(), new HashMap<>());

        System.out.println("=== 解析出的元数据项 ===");
        for (MetaDataItemDefault item : result.getItems()) {
            System.out.println(item);
        }
        System.out.println("\n=== 清理后的公式 ===");
        System.out.println(result.getProcessedFormula());
    }
}