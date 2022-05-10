package com.zjq.dailyrecord.designpattern.creational.factory;

/**
 * @author zjq
 * @date 2022/5/9
 */
public class EmailService implements Warning{
    @Override
    public void sendWarnMSG() {
        System.out.println("邮件123456@qq.com");
    }
}
