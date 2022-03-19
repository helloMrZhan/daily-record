package com.zjq.dailyrecord.java8;

import com.zjq.dailyrecord.entity.Human;
import com.zjq.dailyrecord.entity.User;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zjq
 * @date 2020/6/15 19:57
 * <p>title:JDK1.8新特性</p>
 * <p>description:</p>
 */
public class Java8NewFeature {

    public static void main(String[] args) {

    }

    public static List<User> getListUser(){
        List<User> userList = new ArrayList<>();
        userList.add(new User("张三",3,"北京"));
        userList.add(new User("李四",4,"上海"));
        userList.add(new User("王五",5,"广州"));
        userList.add(new User("赵六",6,"深圳"));
        return userList;
    }

    /**
     * list集合按照某个属性分组
     * @return
     */
    public static Map<String, List<User>>  getUserMap(){
        List<User> list = getListUser();
        Map<String, List<User>> collect = list.stream().collect(Collectors.groupingBy(User::getCity));
        return collect;
    }

    /**
     * list中使用for-each 获取对象里面某个属性集合
     */
    @Test
    public void getListString(){
        List<User> list = getListUser();
        List<String> nameList = new ArrayList<>();
        list.forEach(user -> nameList.add(user.getName()));
        System.out.printf(nameList.toString());
    }

    /**
     * map中使用for-each 获取对象里面某个属性集合
     */
    @Test
    public void getMapString(){
        Map<String, List<User>> userMap = getUserMap();
        List<String> cityList = new ArrayList<>();
        userMap.forEach((key,value) -> cityList.add(key+"/"+value.get(0).getCity()));
        System.out.printf(cityList.toString());
    }

    //----------------filter() start------------------------------------------------
    /**
     * 通过filter过滤需要的用户集合
     *   通过filter过滤元素，通过collect收集元素
     */
    @Test
    public void getUserListFilter(){
        List<User> list = getListUser();
        List<User>  userList = new ArrayList<>();
        userList = list.stream().filter(user -> "北京".equals(user.getCity())).collect(Collectors.toList());
        System.out.println(userList);
    }


    /**
     * 通过filter过滤，findAny + orElse处理查询不到的情况
     */
    @Test
    public void getUserFilterFindAny(){
        List<User> list = getListUser();
        User user1 = list.stream().filter(user -> "东京".equals(user.getCity())).findAny().orElse(new User("小明", 9, "伦敦"));
        System.out.println(user1);
    }


    /**
     * 通过filter实现多条件查询
     */
    @Test
    public void getUserByAgeAndName(){
        List<User>  userList = getListUser();
        User user1 = userList.stream().filter(user -> ("王五".equals(user.getName()) && user.getAge() > 4)).findAny().orElse(null);
        System.out.println(user1);
    }

    /**
     * 通过filter()过滤 map()提取属性
     */
    @Test
    public void getUserFilterMap(){
        List<User>  userList = getListUser();
        String userStr = userList.stream().filter(user -> ("王五".equals(user.getName())))
                .map(user -> user.getName()+"/"+user.getAge()+"/"+user.getCity()).findAny().orElse(null);
        System.out.println(userStr);
    }
    //----------------filter() end------------------------------------------------

    //----------------map() start--------------------------------------------------

    /**
     * 同类型数据转换
     */
    @Test
    public void toUpperCase(){
        List<String> stringList = Arrays.asList("aaa","bbb","ccc","ddd");
        List<String> collect = stringList.stream().map(String::toUpperCase).collect(Collectors.toList());
        System.out.println(collect);
    }

    /**
     * 提取某个属性
     */
    @Test
    public void getCityList(){
        List<User> userList = getListUser();
        userList.add(new User("小哈哈",9,"上海"));
        Set<String> cityList = userList.stream().map(User::getCity).collect(Collectors.toSet());
        System.out.println(cityList);
    }

    /**
     * 一种对象集合转换成另一种对象集合
     */
    @Test
    public void userListToHumanList(){
        List<User> userList = getListUser();
        List<Human> humanList = userList.stream().map(user -> {
            Human human = new Human();
            human.setAge(user.getAge());
            human.setName(user.getName());
            return human;
        }).collect(Collectors.toList());
        System.out.println(humanList);
    }
    //----------------map() end--------------------------------------------------
}
