package com.zjq.dailyrecord.excel.poi;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @description:
 * @author: alan
 * @time: 2021/7/26 17:41
 */
@Service
public class ExcelPoiServiceImpl implements ExcelService {
    //可以转成你想要的格式 yyyy/MM/dd HH:mm:ss 等等
   private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    @Override
    public void export(HttpServletResponse response, HttpServletRequest request) {
        String dateString = format.format(new Date());
        //文件地址名称
        String filePath = "D:/usr/address"+dateString+".xls";
        //excel 表头
        List<String> headers = Arrays.asList("序号","省","市", "区", "无", "属性");
        //所有一级
        List<String> mapOneList = new ArrayList<String>();
        mapOneList.add("广东省");
        mapOneList.add("湖北省");
        //关系map
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        map.put("广东省", Arrays.asList("广州市", "佛山市"));
        map.put("湖北省", Arrays.asList("武汉市", "荆州市"));
        map.put("广州市", Arrays.asList("白云区", "越秀区"));
        map.put("佛山市", Arrays.asList("顺德区", "南海区"));
        //需要显示在excel的信息
        List list = new ArrayList();
        PoiUtil.export(filePath,headers,mapOneList,map,list);
        //获取文件流返回给客户端
        DownloadFileUtil.downloadFile(response, request, filePath);
    }

    @Override
    public List importExcel(String type, InputStream inputStream) {
        List list = new ArrayList();
        try {
            list = PoiUtil.importExcel(inputStream);
        }catch(Throwable t){
            t.printStackTrace();
        }
        return list;
    }
}


