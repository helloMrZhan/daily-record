package com.zjq.dailyrecord.utils;

import com.zjq.dailyrecord.security.Md5Encoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author : zjq
 * @CreateTime : 2019/12/6
 * @Description :
 *  短链接生成
 *  10进制,62进制互转
 **/

@Slf4j
public class ConversionUtils {
    /**
     * 初始化 62 进制数据，索引位置代表字符的数值，比如 A代表10,z代表61等
     */
    private static String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static int scale = 62;

    /**
     * 将数字转为62进制
     *
     * @param num    Long 型数字
     * @param length 转换后的字符串长度，不足则左侧补0
     * @return 62进制字符串
     */
    public static String encode(long num, int length) {
        StringBuilder sb = new StringBuilder();
        int remainder;
        // id混淆算法
        long snum = num & 0xff000000;
        snum += (num & 0x0000ff00) << 8;
        snum += (num & 0x00ff0000) >> 8;
        snum += (num & 0x0000000f) << 4;
        snum += (num & 0x000000f0) >> 4;

        while (snum > scale - 1) {
            /*
              对 scale 进行求余，然后将余数追加至 sb 中，由于是从末位开始追加的，因此最后需要反转（reverse）字符串
             */
            remainder = Long.valueOf(snum % scale).intValue();
            sb.append(chars.charAt(remainder));

            snum = snum / scale;
        }

        sb.append(chars.charAt(Long.valueOf(snum).intValue()));
        String value = sb.reverse().toString();
        log.info("encode id: {}", snum);
        return StringUtils.leftPad(value, length, '0');
    }

    /**
     * 62进制字符串转为数字
     *
     * @param str 编码后的62进制字符串
     * @return 解码后的 10 进制字符串
     */
    public static long decode(String str) {
        /*
          将 0 开头的字符串进行替换
         */
        str = str.replace("^0*", "");
        long num = 0;
        int index;
        for (int i = 0; i < str.length(); i++) {
            /*
              查找字符的索引位置
             */
            index = chars.indexOf(str.charAt(i));
            /*
              索引位置代表字符的数值
             */
            num += (long) (index * (Math.pow(scale, str.length() - i - 1)));
        }
        // id混淆算法
        long snum = num & 0xff000000;
        snum += (num & 0x00ff0000) >> 8;
        snum += (num & 0x0000ff00) << 8;
        snum += (num & 0x000000f0) >> 4;
        snum += (num & 0x0000000f) << 4;

        return snum;
    }



    public static String Md5CodeEncode(String longUrl, int urlLength) {
        if (urlLength < 4 ) {
            urlLength = 8;// defalut length
        }
        StringBuilder sbBuilder = new StringBuilder(urlLength + 2);
        String md5Hex = "";
        int nLen = 0;
        while (nLen < urlLength) {
            // 这个方法是先 md5 再 base64编码 参见
            // https://github.com/ndxt/centit-commons/blob/master/centit-utils/src/main/java/com/centit/support/security/Md5Encoder.java
            md5Hex = Md5Encoder.encodeBase64(md5Hex + longUrl);
            for(int i=0;i<md5Hex.length();i++){
                char c = md5Hex.charAt(i);
                if(c != '/' && c != '+'){
                    sbBuilder.append(c);
                    nLen ++;
                }
                if(nLen == urlLength){
                    break;
                }
            }
        }
        return sbBuilder.toString();
    }


    public static void main(String[] args) {
        System.out.println("62进制：" + encode(1000000001L, 6));
        System.out.println("10进制：" + decode("15t2ps"));


        System.out.println( Md5CodeEncode("https://zhanjq.blog.csdn.net/",6));

    }
}
