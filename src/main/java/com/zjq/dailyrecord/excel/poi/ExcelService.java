package com.zjq.dailyrecord.excel.poi;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

/**
 * @description:
 * @author: alan
 * @time: 2021/7/25 21:33
 */
public interface ExcelService {

     void  export(HttpServletResponse response, HttpServletRequest request);

     List importExcel(String type, InputStream inputStream);
}

