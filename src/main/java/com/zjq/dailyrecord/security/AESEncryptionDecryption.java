package com.zjq.dailyrecord.security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * AES加密解密工具类
 * 密钥（Key）：AES-128需要一个16字节的密钥。在这个示例中，我使用了"1234567890123456"作为密钥。
 * 初始化向量（IV）：AES-CBC模式需要一个16字节的IV。在这个示例中，我使用了"1234567890123456"作为IV。
 * Base64编码：加密后的数据被Base64编码，以便于传输和存储。
 * @author zjq
 */
public class AESEncryptionDecryption {

    public static String decrypt(String encryptedData, String key, String iv) throws Exception {
        // 将密钥和IV从字符串转换为字节数组
        byte[] keyBytes = key.getBytes("UTF-8");
        byte[] ivBytes = iv.getBytes("UTF-8");

        // 创建SecretKeySpec和IvParameterSpec对象
        SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

        // 创建并初始化Cipher对象
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivSpec);

        // 对加密数据进行Base64解码
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);

        // 执行解密操作
        byte[] originalBytes = cipher.doFinal(encryptedBytes);

        // 将解密后的字节数组转换为字符串
        return new String(originalBytes, "UTF-8");
    }

    public static String encrypt(String plainText, String key, String iv) throws Exception {
        // 将密钥和IV从字符串转换为字节数组
        byte[] keyBytes = key.getBytes("UTF-8");
        byte[] ivBytes = iv.getBytes("UTF-8");

        // 创建SecretKeySpec和IvParameterSpec对象
        SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

        // 创建并初始化Cipher对象
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivSpec);

        // 执行加密操作
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes("UTF-8"));

        // 对加密数据进行Base64编码
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static void main(String[] args) {
        try {
            String plainText = "这是要加密的文本";
            String key = "1234567890123456"; // 16字节的密钥
            String iv = "1234567890123456"; // 16字节的IV

            String encryptedData = encrypt(plainText, key, iv);
            System.out.println("加密后的数据: " + encryptedData);

            String decryptedData = decrypt(encryptedData, key, iv);
            System.out.println("解密后的数据: " + decryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}