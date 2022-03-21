package com.zjq.dailyrecord.designpattern.spring.factory;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 测试工厂模式
 * @author zjq
 * @date 2022/3/20 16:04
 * <p>title:</p>
 * <p>description:</p>
 */
public class FactoryDesignTest {

    @Test
    public void test1(){
        ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = (UserServiceImpl) app.getBean("userService");
        userService.save();
        System.out.println(userService);
    }

}
