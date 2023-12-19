package com.zjq.dailyrecord.security;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;

/**
 * RSA加密和解密
 * @author 共饮一杯无
 */
public class RSAEncryptionDecryption {
    public static void main(String[] args) throws Exception {
        String originalText = "Hello, RSA encryption and decryption!";
        
        // 将公钥和私钥的Base64编码字符串转换为PublicKey和PrivateKey对象
        String publicKeyBase64 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhRJqXuMDyyc4b3+LrsZqwh+sZtV3n2pwjkWZ+SIkfW3GlrVPEQmGDbCB2xJ3coSc/IQ5ukkdh1ArTzf69kmn3zNZT34ZJgYjLNnvi9I2dBRZkARV2ERFhPYZsUt8WecSGt29SK22NsctMkSroRmsLRMUArmZ2r3knMrhy54PLvoeXwvDdpXC19EsioK5I7Huh29G+c3Bi8IWySR4/U2kpH+8CU2iZGiChwIZ6qqJgvaVbUuSdksHFnrVbl1LjqGKlb+Vos16UnluPlW4PGJMCfRYZcPqLSm728qT+jQFIUK17yAeznIvx5nccg6ke1GgnwhqeDicPuKnj4FKFm33/wIDAQAB";
        String privateKeyBase64 = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCFEmpe4wPLJzhvf4uuxmrCH6xm1XefanCORZn5IiR9bcaWtU8RCYYNsIHbEndyhJz8hDm6SR2HUCtPN/r2SaffM1lPfhkmBiMs2e+L0jZ0FFmQBFXYREWE9hmxS3xZ5xIa3b1IrbY2xy0yRKuhGawtExQCuZnaveScyuHLng8u+h5fC8N2lcLX0SyKgrkjse6Hb0b5zcGLwhbJJHj9TaSkf7wJTaJkaIKHAhnqqomC9pVtS5J2SwcWetVuXUuOoYqVv5WizXpSeW4+Vbg8YkwJ9Fhlw+otKbvbypP6NAUhQrXvIB7Oci/HmdxyDqR7UaCfCGp4OJw+4qePgUoWbff/AgMBAAECggEAWWJOSuAn6yy0DsjYlZQ3n59Q2V4n1M/VPOtpiluxsQKsswykSGhiQA3Am9timmyTWlaixAtap0plXPfYPdipxxYhtnCYCd9zfywAaKXR59THeCJBW1w4aiA4j8uJgoXgtmUdQJVWYKMXK73Onw60hS5ccZwjyTdmOR9Z3cCUqFNmX9EIAj9jUE9/nASNgnGNH5ULspaBUSH59B0D/2kNUexMrteShtlxKL73iFdptGu68NLk05GvghLG3o0HMJtOIyF+kj6x0BtPcD5xh7YxN6PTdrxnj4tmKsAesc38NBJphFFFmvxY5B9m9gKMOBQcGVW0By6AJLbE5Pj1w5GlMQKBgQDJ7+XQc3Q8VgXQZYpO2CA8Kygkls7GTsXwblB6u0aYT7uhht4Dwk2xCtkRWoUri6rVkcOKKY/SrU4GvVyK8E5AMHfOjTc1M6GQ6UOj760NUMqUwwzR45pUFKLtYq+gOlWqHz0Vu84DCQqU7nhJGqv5cMUoZRkTrqV6zPq/oWLa6QKBgQCosroKI3NfvkaTxYboNF8Bn6j1nzCrNW3VrtZvXXbeTWTxSgH01p45IcPPEfauQqHHzFSzrVP4HL9PNz8SYpwhS61i3PX5S2ftLRKsfOheYKWG7l5clu80SZfAcpXblp4QTZmHdp67dp2XMEFi/3VGDhZU/LCpLMvIUs/8MpmapwKBgBXALD3Gocd58Ihg14PkjZxNfbZrM/xyManTCAIgN9tiAzDDyRgYjqu6ImVXHa7yDUWRvMEd9urXVect8FDaz2LklZL+7OpjFEz6gxmeUEJ16Ewbsj7NSCs0SdRN4+LbRazcToUPxIHZMHWYNgaRw+JLPkE6mnffQN24RG3toSs5AoGBAIgoEI2kRTduXIpiL9t0gYXO9lCgVmio6+g+f+ZMemc78g/pWqDhI70a6m5TolTNhMO8wFRwvcgQc7wc6/QL0NXyvZOAoaq+2LeN3HeJLQcXXCIGe/ShAZmjGC8EjL052INyDktOSxkkyFbBZNThOCb9sbqQZIl2lVcut51mvaEbAoGBALwLpxIjj7N+dxkbScZCTWCgSPZ6t9y5rO9VkLtJ31aDAFqXljh4hphHhnsUq9z2pT3fo5mNRnaYutIixmzxQSQlzmjvnzFe+ZHFXMHm2l1fgOi5ByV9a/prUmyTuLuiwCf1/Q+E+qFPNnl5Actbamqk26zlMbZVTK6lrTM5PN+/";
        
        PublicKey publicKey = KeyFactory.getInstance("RSA")
                .generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyBase64)));
        PrivateKey privateKey = KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyBase64)));

        // 使用公钥加密数据
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = encryptCipher.doFinal(originalText.getBytes());

        // 使用私钥解密数据
        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = decryptCipher.doFinal(encryptedBytes);

        String decryptedText = new String(decryptedBytes);
        System.out.println("加密后的数据: " + Base64.getEncoder().encodeToString(encryptedBytes));
        System.out.println("解密后的数据: " + decryptedText);
    }
}
