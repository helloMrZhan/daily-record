package com.zjq.dailyrecord.excel.easyexcel.controller;

import com.zjq.dailyrecord.excel.easyexcel.entity.CeshiTemplateVo;
import com.zjq.dailyrecord.excel.easyexcel.service.ExcelDemoService;
import com.zjq.dailyrecord.excel.easyexcel.util.ExcelUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

    /**
     * 数据模板列表导出（根据实体注释导出）
     *
     */
    @ApiOperation("数据模板列表导出")
    @GetMapping("/export")
    public void queryPageExport(HttpServletResponse response) {

        List<CeshiTemplateVo.QueryPage> records = new ArrayList<>();
        records.add(new CeshiTemplateVo.QueryPage()
                .setCeshiTemplateCode("ceshiTemplateCode1")
                .setCeshiTemplateName("测试数据模板名称1")
                .setCreateTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now())
                .setOrganizeBy(1L)
                .setOrganizeByName("测试机构1")
                .setRemark("测试数据模板描述1"));
        records.add(new CeshiTemplateVo.QueryPage()
                .setCeshiTemplateCode("ceshiTemplateCode2")
                .setCeshiTemplateName("测试数据模板名称2")
                .setCreateTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now())
                .setOrganizeBy(2L)
                .setOrganizeByName("测试机构2")
                .setRemark("测试数据模板描述2"));
        ExcelUtil.writeExcelFile(response, "测试数据模板列表导出111", CeshiTemplateVo.QueryPage.class, records);
    }


    /**
     * 数据模板列表导出（根据实体注释导出多个sheet页面）
     *
     */
    @ApiOperation("数据模板列表导出")
    @GetMapping("/exportMultiSheet")
    public void exportMultiSheet(HttpServletResponse response) {

        List<CeshiTemplateVo.QueryPage> records = new ArrayList<>();
        records.add(new CeshiTemplateVo.QueryPage()
                .setCeshiTemplateCode("ceshiTemplateCode1")
                .setCeshiTemplateName("测试数据模板名称1")
                .setCreateTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now())
                .setOrganizeBy(1L)
                .setOrganizeByName("测试机构1")
                .setRemark("测试数据模板描述1"));
        records.add(new CeshiTemplateVo.QueryPage()
                .setCeshiTemplateCode("ceshiTemplateCode2")
                .setCeshiTemplateName("测试数据模板名称2")
                .setCreateTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now())
                .setOrganizeBy(2L)
                .setOrganizeByName("测试机构2")
                .setRemark("测试数据模板描述2"));
        ExcelUtil.writeExcelFileMultiSheet(response, "测试数据模板列表导出111", CeshiTemplateVo.QueryPage.class, records);
    }
}
