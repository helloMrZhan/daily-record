package com.zjq.dailyrecord.designpattern.creational.factory;

/**
 * 告警信息创建工厂
 * @author zjq
 * @date 2022/5/9
 */
public class WarningFactory {

    /**
     * 想要什么类型的告警信息，都由工厂创建
     * @param type
     * @return
     */
    public static Warning getWarningMsg(String type){
        if(type==null){
            return null;
        }
        switch (type){
            case "msg":
                return  new MessageService();
            case "email":
                return new EmailService();
            default:
                throw new RuntimeException("没有此告警方式");
        }
    }
}
