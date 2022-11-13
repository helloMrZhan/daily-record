package com.zjq.dailyrecord.excel.easyexcel.controller;

import com.zjq.dailyrecord.excel.easyexcel.service.ExcelDemoService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * EasyExcel案例
 * @author zjq
 */
@RestController
@RequestMapping("/easyExcel")
public class ExcelDemoController {

    @Autowired
    private ExcelDemoService excelDemoService;

    @GetMapping("/exportTemplate")
    public void exportTemplate(HttpServletResponse httpServletResponse) throws IOException {
        excelDemoService.exportTemplate(httpServletResponse);
    }

}
