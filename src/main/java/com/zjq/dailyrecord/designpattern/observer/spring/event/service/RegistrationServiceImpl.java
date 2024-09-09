package com.zjq.dailyrecord.designpattern.observer.spring.event.service;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 注册服务实现类
 * @author zjq
 */
@Service
@Slf4j
public class RegistrationServiceImpl implements IRegistrationService {

    private final ConcurrentHashMap<Class, Class> registrationMap = new ConcurrentHashMap<>();


    @Override
    public void doRegister(Class dtoClass, Class queryClass) {
        registrationMap.put(dtoClass, queryClass);
    }

    @Override
    public Class getRegistry(Class dtoClass) {
        Class queryClass = registrationMap.get(dtoClass);
        Assert.notNull(queryClass, StrUtil.format("{}的查询实现类未找到", dtoClass.toString()));
        return queryClass;
    }
}