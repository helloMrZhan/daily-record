package com.zjq.dailyrecord.utils;

import org.springframework.util.StringUtils;

import java.util.regex.Pattern;


/**
 * 常用正则表达式校验工具类
 * @author zjq
 */
public class RegExUtil {

    /***纯数字*/
    public static final String REGEX_NUMBER = "^[0-9]*$";
    /**非0开头数字*/
    public static final String REGEX_NUMBER_NOTZERO = "^(0|[1-9][0-9]*)$";
    /**邮箱地址*/
    public static final String REGEX_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    /**域名*/
    public static final String REGEX_DOMAIN = "[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(/.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+/.?";
    /**URL*/
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
    /**IP*/
    public static final String REGEX_IP_ADDR = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
    /**手机号码*/
    public static final String REGEX_PHONE_NUMBER = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
//^[1][2,3,4,5,6,7,8,9][0-9]{9}$"手机号码这个要特别注意，根据时间推移可能会开放新号码段

    /**身份证*/
    public static final String REGEX_ID_CARD = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
            "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
    /**账号(字母开头，允许5-16字节，允许字母数字下划线)*/
    public static final String REGEX_ACCOUNT = "^[a-zA-Z][a-zA-Z0-9_]{4,15}$";
    /**强密码(必须包含大小写字母和数字的组合，不能使用特殊字符，长度在8-10之间)*/
    public static final String REGEX_PASSWORD = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,10}$";
    /**中文(只能输入中文)*/
    public static final String REGEX_CHINESE = "[\\u4e00-\\u9fa5]+";


    public static boolean isNumber(String value) {

        return Pattern.matches(REGEX_NUMBER, value);
    }
    public static boolean isNumberNotZeroBegin(String value) {

        return Pattern.matches(REGEX_NUMBER_NOTZERO, value);
    }

    /**
     * 指定多少位数字
     * @param value
     * @param count
     * @return
     */
    public static boolean isPureNumber(String value, int count) {
        String REGEX_PURE_NUMBER = "^\\d" + "{" + count + "}$";
        return Pattern.matches(REGEX_PURE_NUMBER, value);
    }

    /**
     * 指定至少多少位数字
     * @param value
     * @param count
     * @return
     */
    public static boolean isPureNumberLeast(String value, int count) {

        String REGEX_PURE_NUMBER = "^\\d" + "{" + count + ",}$";
        return Pattern.matches(REGEX_PURE_NUMBER, value);
    }

    public static String phoneNumberEncrypt(String phoneNumber){
        if(!StringUtils.isEmpty(phoneNumber)) {
            String phoneResult = phoneNumber.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
            return phoneResult;
        }
        return phoneNumber;
    }


    public static boolean isEmail(String value) {

        return Pattern.matches(REGEX_EMAIL, value);
    }
    public static boolean isDomain(String value) {

        return Pattern.matches(REGEX_DOMAIN, value);
    }
    public static boolean isUrl(String value) {

        return Pattern.matches(REGEX_URL, value);
    }
    public static boolean isIpAddr(String value) {

        return Pattern.matches(REGEX_IP_ADDR, value);
    }
    public static boolean isPhoneNumber(String value) {

        return Pattern.matches(REGEX_PHONE_NUMBER, value);
    }
    public static boolean isAccount(String value) {

        return Pattern.matches(REGEX_ACCOUNT, value);
    }
    public static boolean isPassword(String value) {

        return Pattern.matches(REGEX_PASSWORD, value);
    }
    public static boolean isChinese(String value) {

        return Pattern.matches(REGEX_CHINESE, value);
    }
    public static boolean isIdCard(String value) {
        boolean matches = value.matches(REGEX_ID_CARD);
        if (matches) {

            if (value.length() == 18) {
                try {
                    char[] charArray = value.toCharArray();
                    //前十七位加权因子
                    int[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
                    //这是除以11后，可能产生的11位余数对应的验证码
                    String[] idCardY = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
                    int sum = 0;
                    for (int i = 0; i < idCardWi.length; i++) {
                        int current = Integer.parseInt(String.valueOf(charArray[i]));
                        int count = current * idCardWi[i];
                        sum += count;
                    }
                    char idCardLast = charArray[17];
                    int idCardMod = sum % 11;
                    if (idCardY[idCardMod].toUpperCase().equals(String.valueOf(idCardLast).toUpperCase())) {
                        return true;
                    } else {
                        System.out.println("身份证最后一位:" + String.valueOf(idCardLast).toUpperCase() +
                                "错误,正确的应该是:" + idCardY[idCardMod].toUpperCase());
                        return false;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("异常:" + value);
                    return false;
                }
            }

        }
        return matches;
    }

    public static void main(String[] args) {

        System.out.println(phoneNumberEncrypt("18688666688"));
    }


}
