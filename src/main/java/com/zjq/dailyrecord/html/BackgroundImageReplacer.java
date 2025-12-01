package com.zjq.dailyrecord.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class BackgroundImageReplacer {

    public static void main(String[] args) {
        String html = "<div class=\"container\" data-background-image=\"backgroundImg\" style=\"background-image: url('/custody/images/template-default-bg.png');\">Content here...</div>";
        String base64Image = convertImageToBase64("D:\\img\\KTWQIJ2DXBH0KS3D.png");

        String modifiedHtml = replaceBackgroundImage(html, base64Image);
        System.out.println(modifiedHtml);
    }

    /**
     * 将图片转换为Base64编码
     *
     * @param imagePath 图片路径
     * @return Base64编码后的图片数据
     */
    private static String convertImageToBase64(String imagePath) {
        try {
            byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
            return "data:image/png;base64," + Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read image file", e);
        }
    }

    /**
     * 替换HTML中的背景图片
     *
     * @param html          HTML字符串
     * @param base64Image   Base64编码的图片数据
     * @return 替换后的HTML字符串
     */
    private static String replaceBackgroundImage(String html, String base64Image) {
        Document doc = Jsoup.parse(html);
        Elements divs = doc.select(".container[data-background-image=backgroundImg]");

        for (Element div : divs) {
            div.attr("style", "background-image: url(" + base64Image + ");");
        }

        return doc.html();
    }
}
