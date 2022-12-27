package com.zjq.dailyrecord.excel.easyexcel.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.zjq.dailyrecord.excel.easyexcel.entity.UserDTO;
import com.zjq.dailyrecord.excel.easyexcel.util.EasyExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * @author zjq
 */
@RestController
@Slf4j
public class exportController {

    @GetMapping("/exportSelectedExcel")
    public void export(HttpServletRequest request, HttpServletResponse response){
        String filename = "测试";
        try {
            String userAgent = request.getHeader("User-Agent");

            if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
                // 针对IE或者以IE为内核的浏览器：
                filename = java.net.URLEncoder.encode(filename, "UTF-8");
            } else {
                // 非IE浏览器的处理:
                filename = new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            }
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", filename + ".xlsx"));
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", -1);
            response.setCharacterEncoding("UTF-8");

            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();
            WriteSheet writeSheet = EasyExcelUtil.writeSelectedSheet(UserDTO.class, 0, "测试sheet");
            excelWriter.write(new ArrayList<String>(), writeSheet);
            excelWriter.finish();
        } catch (UnsupportedEncodingException e) {
            log.error("导出Excel编码异常", e);
        } catch (IOException e) {
            log.error("导出Excel文件异常", e);
        }
    }
}
