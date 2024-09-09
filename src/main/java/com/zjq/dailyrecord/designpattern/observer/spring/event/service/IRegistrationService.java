package com.zjq.dailyrecord.designpattern.observer.spring.event.service;

/**
 * 注册服务
 * @author zjq
 */
public interface IRegistrationService {

    /**
     * 注册
     * @param dtoClass dto类
     * @param queryClass 查询类
     */
    void doRegister(Class dtoClass, Class queryClass);

    /**
     * 获取注册信息
     * @param dtoClass dto类
     * @return 注册信息
     */
    Class getRegistry(Class dtoClass);
}