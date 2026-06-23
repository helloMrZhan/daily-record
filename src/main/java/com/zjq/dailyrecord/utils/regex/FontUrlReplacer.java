package com.zjq.dailyrecord.utils.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FontUrlReplacer {
    public static void main(String[] args) {
        String content = "@font-face {\n" +
                         "  font-family: SourceHanSansSC-Bold;\n" +
                         "  src: url(\"https://minioserver.hello.cn/hello-web/font/SourceHanSansSC-Bold.otf\");\n" +
                         "  font-weight: normal;\n" +
                         "  font-style: normal;\n" +
                         "}";
        String newFontUrl = "file:///app/SourceHanSansSC-Regular.otf";

        // 定义正则表达式
        String regex = "url\\(\"([^\" ]+SourceHanSansSC-Bold\\.otf)\"\\)";
        Pattern pattern = Pattern.compile(regex);

        // 创建Matcher对象
        Matcher matcher = pattern.matcher(content);

        // 使用Matcher的replaceAll方法替换匹配的内容
        String replacedContent = matcher.replaceAll("url(\"" + newFontUrl + "\")");

        System.out.println(replacedContent);
    }
}
