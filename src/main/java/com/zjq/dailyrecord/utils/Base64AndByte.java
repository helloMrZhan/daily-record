package com.zjq.dailyrecord.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;

/**
 * Base64和byte数组互转
 * @author zjq
 * @date 2022/4/20
 */
public class Base64AndByte {

    public static void main(String[] args) throws IOException {

        //方式一：jdk的工具类javax.xml.bind.DatatypeConverter
        String str = "base64字符串";
        //byte[]转base64
        String encode = DatatypeConverter.printBase64Binary(str.getBytes());
        System.out.println("转base64后："+encode);
        //Base64转byte[]
        byte[] decode= DatatypeConverter.parseBase64Binary(encode);
        System.out.println("base64转回："+new String(decode));

        //方式二：jdk的工具类sun.misc.BASE64Decoder和sun.misc.BASE64Encoder
        //byte[]转base64
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String encode1 = base64Encoder.encode(str.getBytes());
        System.out.println("转base64后："+encode1);
        //Base64转byte[]
        BASE64Decoder base64Decoder = new BASE64Decoder();
        byte[] decodeBuffer = base64Decoder.decodeBuffer(encode1);
        System.out.println("base64转回："+new String(decodeBuffer));
    }

}
