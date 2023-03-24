package com.zjq.dailyrecord.excel.openoffice;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * 在线预览word、excel、ppt
 * @author zjq
 * @date 2023/3/24 13:59
 * @description:
 */
public class OnlinePreviewController {

    @Autowired
    private OnlinePreviewService onlinePreview;

    @ApiOperation(value = "系统文件在线预览接口")
    @PostMapping("/api/file/onlinePreview")
    public void onlinePreview(@RequestParam("url") String url, HttpServletResponse response) throws Exception{
        onlinePreview.onlinePreview(url,response);
    }
}
