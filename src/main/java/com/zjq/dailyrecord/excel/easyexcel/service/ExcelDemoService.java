package com.zjq.dailyrecord.excel.easyexcel.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.google.common.collect.Lists;
import com.zjq.dailyrecord.excel.easyexcel.handler.SelectSheetWriteHandler;
import com.zjq.dailyrecord.excel.easyexcel.service.entity.BusinessField;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * easyExcel 服务端案例
 * @author zjq
 * @date 2022/11/4 20:10
 * <p>title:</p>
 * <p>description:</p>
 */
@Service
public class ExcelDemoService {

    /**
     * 导出模板
     * @param response
     */
    public void exportTemplate(HttpServletResponse response) throws IOException {

        String zipName = "模板.zip";
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader("Content-Disposition", "attachment;filename*=UTF-8''" + URLEncoder.encode(zipName, "UTF-8"));
        ServletOutputStream out = response.getOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(out);


//        List<BusinessField> field = baseMapper.findBusinessFieldByViewId(viewid);
        //根据表id查询父表id
        List<BusinessField> parentFieldList = getBusinessFieldList();
        //定义表头
        List<List<String>> headList = new ArrayList<>();
        //定义数据体
        List<List<Object>> dataList = new ArrayList<>();
        // 指定标红色的列
        List<Integer> columns = Arrays.asList();
        // 指定批注
        HashMap<Integer, String> annotationsMap = new HashMap<>();
        HashMap<Integer, List<String>> dropDownMap = new HashMap<>();
        //主表字段
        for (int i = 0;i<parentFieldList.size();i++){
            BusinessField businessField = parentFieldList.get(i);
            headList.add(Lists.newArrayList(businessField.getName()));
            if (StringUtils.isNotBlank(businessField.getControlType())){
                if (businessField.getControlType().contains("select")){
                    List<String> tDataDictionaries = new ArrayList<>();
                    tDataDictionaries.add("男");
                    tDataDictionaries.add("女");
//                  存储需要下拉框的值，这里的key是需要设置为下拉框的列数，value是下拉框的值，是list
                    if (tDataDictionaries != null && tDataDictionaries.size()>0) {
                        dropDownMap.put(i,tDataDictionaries);
                    }
                }
            }
        }
        //子表字段
//        for (int i = 0;i<field.size();i++){
//            BusinessField businessField = field.get(i);
//            headList.add(Lists.newArrayList(businessField.getName()));
//            if (StringUtils.isNotBlank(businessField.getControlType())){
//                if (businessField.getControlType().contains("select")){
//                    List<String> tDataDictionaries = tDataDictionaryTempMapper.getNameByPid(businessField.getDictionary());
////                         存储需要下拉框的值，这里的key是需要设置为下拉框的列数，value是下拉框的值，是list
//                    if (tDataDictionaries != null && tDataDictionaries.size()>0){
//                        dropDownMap.put(i+parentFieldList.size(),tDataDictionaries);
//                    }
//                }
//            }
//        }
        ExcelWriter excelWriter = EasyExcel.write().excelType(ExcelTypeEnum.XLS).build();
        //构建一个sheet页
        WriteSheet writeSheet = EasyExcel.writerSheet("sheet1").build();
//            TltleHandler titleHandler = new TltleHandler(columns, IndexedColors.RED.index,annotationsMap,dropDownMap);
//            ExayExcelUtils.writeExcelWithModel(response.getOutputStream(), dataList, headList, "sheet1", (CellWriteHandler) titleHandler);
// 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 单元格策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 初始化表格样式
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
// SelectSheetWriteHandler(dropDownMap)  是设置下拉框的类
        WriteTable writeTable = EasyExcel.writerTable(0).head(headList).registerWriteHandler(horizontalCellStyleStrategy).registerWriteHandler(new SelectSheetWriteHandler(dropDownMap)).needHead(Boolean.TRUE).build();
        excelWriter.write(dataList, writeSheet, writeTable);
        // 开始导出
//            excelWriterSheetBuilder.doWrite(dataList);
        Workbook workbook = excelWriter.writeContext().writeWorkbookHolder().getWorkbook();
        //创建压缩文件
        ZipEntry zipEntry = new ZipEntry("压缩文件名称.xls");
        zipOutputStream.putNextEntry(zipEntry);

        //将excel对象以流的形式写入压缩流
        workbook.write(zipOutputStream);



        zipOutputStream.flush();
        zipOutputStream.close();

    }

    private List<BusinessField> getBusinessFieldList() {
        List<BusinessField> businessFieldList = new ArrayList<>();
        BusinessField businessField = new BusinessField();
        businessField.setName("姓名");
        businessField.setControlType("select");
        businessFieldList.add(businessField);
        return businessFieldList;
    }


}
