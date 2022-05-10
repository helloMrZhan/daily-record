package com.zjq.dailyrecord.designpattern.creational.factory;

/**
 * 工厂模式验证
 * @author zjq
 */
public class FactoryPatternDemo {
    public static void main(String[] args) {
        Warning message = WarningFactory.getWarningMsg("msg");
        message.sendWarnMSG();
        Warning email = WarningFactory.getWarningMsg("email");
        email.sendWarnMSG();
    }
}