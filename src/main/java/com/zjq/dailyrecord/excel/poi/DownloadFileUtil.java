package com.zjq.dailyrecord.excel.poi;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @description:
 * @author: alan
 * @time: 2020/9/27 18:01
 */
public class DownloadFileUtil {
    /**
     * 下载文件
     *
     * @param response
     * @param request
     * @param filePath 文件地址
     * @throws Exception
     */
    public static void downloadFile(HttpServletResponse response, HttpServletRequest request, String filePath) {
        try {
            //获取文件
            File file = new File(filePath);
            String fileName = file.getName();
            response.reset();
            ServletOutputStream out = response.getOutputStream();
            request.setCharacterEncoding("UTF-8");
            int BUFFER = 1024 * 10;
            byte data[] = new byte[BUFFER];
            BufferedInputStream bis = null;
            //获取文件输入流
            InputStream inputStream = new BufferedInputStream(new FileInputStream(filePath));
            // 以流的形式下载文件。
            DataInputStream fis = new DataInputStream(inputStream);
            int read;
            bis = new BufferedInputStream(fis, BUFFER);
            response.setContentType("application/OCTET-STREAM");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            while ((read = bis.read(data)) != -1) {
                out.write(data, 0, read);
            }
            fis.close();
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            File file = new File(filePath);
            //删除临时文件
            if (file.exists()) {
                file.delete();
            }
        }
    }
}

