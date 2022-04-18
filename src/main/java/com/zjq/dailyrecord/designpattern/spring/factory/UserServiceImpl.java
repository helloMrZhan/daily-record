package com.zjq.dailyrecord.designpattern.spring.factory;

/**
 * @author zjq
 * @date 2022/3/20 16:04
 * <p>title:</p>
 * <p>description:</p>
 */
public class UserServiceImpl implements UserService {


    public UserServiceImpl() {
    }

    @Override
    public void save() {

        System.out.println("用户保存完成.....");
    }
}
