package com.zjq.dailyrecord.entity;

import lombok.Data;

/**
 * @author zjq
 * @date 2020/6/15 19:58
 * <p>title:用户</p>
 * <p>description:</p>
 */
@Data
public class User {


    private String name;

    private Integer age;

    private String city;

    private Integer score;

    public  User(){}

    public User(String name,Integer age,String city){
        this.name = name;
        this.age = age;
        this.city = city;
    }

    public User(String name,Integer age,String city,Integer score){
        this.name = name;
        this.age = age;
        this.city = city;
        this.score = score;
    }
}
