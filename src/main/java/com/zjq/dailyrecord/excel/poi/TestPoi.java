package com.zjq.dailyrecord.excel.poi;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: alan
 * @time: 2021/7/26 14:11
 */
@Controller
@RequestMapping("/api/poi")
public class TestPoi {
    private final Logger log = LoggerFactory.getLogger(TestPoi.class);

    @Resource(type = ExcelPoiServiceImpl.class)
    private ExcelService excelService;

    @Autowired
    private ObjectMapper objectMapper;


    @GetMapping("/test/exportExcel")
    @ApiOperation(value = "导出Excel")
    public void exportExcel(HttpServletResponse response, HttpServletRequest request) {
        excelService.export(response, request);
    }

    @PostMapping("/test/importExcel")
    @ApiOperation(value = "导入Excel")
    public ResponseEntity importFile(@RequestParam("file") MultipartFile multipartFile) {
        try {
            InputStream inputStream = multipartFile.getInputStream();
            List<String> fileContents = new ArrayList<String>();
            fileContents = excelService.importExcel("", inputStream);
            String json = objectMapper.writeValueAsString(fileContents);
            log.info("导入的数据：{}", json);
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return new ResponseEntity<>(null, null, HttpStatus.OK);

    }

}


