package com.zjq.dailyrecord.utils.base64;

import com.zjq.dailyrecord.utils.zip.ZipUtils;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * 测试Base64工具类
 * @author zjq
 * @date 2022/5/13
 */
public class Base64Test {

    /**
     * 测试下载远程url的文件，转换成base64编码
     * @throws Exception
     */
    @Test
    public void testUrlFileToBase64() throws Exception {
        String BASE64Str = Base64Util.file("https://zbsz-pay-test.oss-cn-hangzhou.aliyuncs.com/upload/20220513/1652423425942-91321391690267369M");
        System.out.println("BASE64Str:"+BASE64Str);
    }
}
