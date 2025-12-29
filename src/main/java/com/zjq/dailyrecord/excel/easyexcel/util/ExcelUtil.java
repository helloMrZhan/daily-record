package com.zjq.dailyrecord.excel.easyexcel.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.event.SyncReadListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.zjq.dailyrecord.dateAndTime.DateUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class ExcelUtil {

    /**
     * 数据导出限制，最多5000行
     */
    public static final long EXPORT_LIMIT = 5000L;

    /**
     * 页面上传，读取excel，数据存储
     */
    public static <T> void readSaveExcelFile(InputStream inputStream, ReadListener<T> readListener) {
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(inputStream, readListener).sheet().doRead();
    }

    /**
     * 页面上传，读取excel，数据存储
     *
     * @author zjq
     * @date 2020/8/6 14:43
     */
    public static <T> void readSaveExcelFile(InputStream inputStream, Class<T> headCls, ReadListener<T> readListener) {
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(inputStream, headCls, readListener).sheet().doRead();
    }

    /**
     * 同步读取excel文件数据
     *
     * @param inputStream
     * @param headCls
     * @param readListener
     * @param <T>
     * @return
     */
    public static <T> List<Object> syncReadExcelFile(InputStream inputStream, Class<T> headCls, SyncReadListener readListener) {
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        return EasyExcel.read(inputStream, headCls, readListener).sheet().doReadSync();
    }

    /**
     * 写入excel, 页面下载
     *
     * @author zjq
     * @date 2020/8/6 11:29
     */
    public static <T> void writeExcelFile(HttpServletResponse response, String fileName, Class<T> headCls, List<T> dataList) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            String fileNameEncode = new String((fileName + ".xlsx").getBytes(), StandardCharsets.ISO_8859_1);
            response.setHeader("Content-disposition", "attachment;filename=" + fileNameEncode);
            EasyExcel.write(response.getOutputStream(), headCls).autoCloseStream(Boolean.FALSE).sheet(fileName)
                    .doWrite(dataList);
            // EasyExcel.write(new File("C:\\Users\\kimi\\Desktop\\" + fileName+ ".xlsx"), headCls).autoCloseStream(Boolean.FALSE).sheet(fileName)
            //     .doWrite(dataList);
        } catch (Exception e) {
            log.error("下载失败", e);
        }
    }


    /**
     * 写入excel, 页面下载
     *
     * @author zjq
     * @date 2020/8/6 11:29
     */
    public static <T> void writeExcelFileMultiSheet(HttpServletResponse response, String fileName, Class<T> headCls, List<T> dataList) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            String fileNameEncode = new String((fileName + ".xlsx").getBytes(), StandardCharsets.ISO_8859_1);
            response.setHeader("Content-disposition", "attachment;filename=" + fileNameEncode);
            // 获取输出流（注意：不要提前关闭）
            OutputStream outputStream = response.getOutputStream();

            // 构建 ExcelWriter
            ExcelWriter excelWriter = EasyExcel.write(outputStream, headCls)
                    .autoCloseStream(Boolean.FALSE) // 不自动关闭流，由我们控制
                    .build();

            // 第一个 Sheet
            WriteSheet sheet1 = EasyExcel.writerSheet("Sheet1名称")
                    .head(headCls)
                    .build();
            excelWriter.write(dataList, sheet1);

            // 第二个 Sheet（可以不同 head 和 data）
            WriteSheet sheet2 = EasyExcel.writerSheet("Sheet2名称")
                    .head(headCls) // 可以是不同的表头类
                    .build();
            excelWriter.write(dataList, sheet2);

            // 第三个 Sheet（动态表头）
            List<List<String>> head = Arrays.asList(
                    Arrays.asList("姓名"),
                    Arrays.asList("年龄"),
                    Arrays.asList("城市")
            );

            WriteSheet sheet = EasyExcel.writerSheet("动态表头")
                    .head(head)
                    .build();

            List<List<Object>> data = Arrays.asList(
                    Arrays.asList("张三", 25, "北京"),
                    Arrays.asList("李四", 30, "上海")
            );

            excelWriter.write(data, sheet);

            // 可以继续添加更多 sheet...

            // 最后 finish()，真正写出并释放资源
            excelWriter.finish();

            // 注意：此时不要手动 close outputStream（如果 autoCloseStream=false）
            // 但如果 response 是 ServletOutputStream，通常由容器管理，不需手动 close
        } catch (Exception e) {
            log.error("下载失败", e);
        }
    }

    /**
     * 写入excel, 页面下载
     *
     * @author zjq
     * @date 2020/8/6 11:29
     */
    public static <T> void writeExcelFile(HttpServletResponse response, String fileName, List<List<String>> head, List<T> dataList) {
        writeExcelFile(response, fileName, head, dataList, "iso-8859-1");
    }

    /**
     * 写入excel, 页面下载
     *
     * @author zjq
     * @date 2020/8/6 11:29
     */
    public static <T> void writeExcelFile(HttpServletResponse response, String fileName, List<List<String>> head, List<T> dataList, String charset) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            String fileNameEncode = new String((fileName + StringPool.UNDERSCORE + DateUtil.localDateTime2Str() + ".xlsx").getBytes(), charset == null ? StandardCharsets.UTF_8.toString() : charset);
            response.setHeader("Content-disposition", "attachment;filename=" + fileNameEncode);
            EasyExcel.write(response.getOutputStream())
                    .autoCloseStream(Boolean.FALSE)
                    .sheet(fileName)
                    .head(head)
                    .doWrite(dataList);
        } catch (Exception e) {
            log.error("下载失败", e);
        }
    }

    /**
     * 写入excel, 页面下载
     *
     * @author zjq
     * @date 2020/8/6 11:29
     */
    public static <T> void writeExcelFile4Get(HttpServletResponse response, String fileName, List<List<String>> head, List<T> dataList) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            String fileNameEncode = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString()) + ".xlsx";
            response.setHeader("Content-disposition", "attachment;filename=" + fileNameEncode);
            EasyExcel.write(response.getOutputStream())
                    .autoCloseStream(Boolean.FALSE)
                    .sheet(fileName)
                    .head(head)
                    .doWrite(dataList);
        } catch (Exception e) {
            log.error("下载失败", e);
        }
    }
}
