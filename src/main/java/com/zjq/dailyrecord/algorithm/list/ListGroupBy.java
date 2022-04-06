package com.zjq.dailyrecord.algorithm.list;

import com.zjq.dailyrecord.entity.User;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * List集合按照某个字段或者属性分组
 * @author zjq
 * @date 2022/3/21
 */
public class ListGroupBy {

    public static List<User> getUserList(){
        List<User> userList = new ArrayList<>();
        userList.add(new User("小明",11,"北京",66));
        userList.add(new User("小红",12,"上海",99));
        userList.add(new User("小詹",14,"杭州",77));
        userList.add(new User("小龙",16,"伦敦",55));
        userList.add(new User("小斯",9,"杭州",33));
        userList.add(new User("小詹",9,"上海",33));
        userList.add(new User("小布",12,"伦敦",88));
        userList.add(new User("小布",12,"上海",55));
        return userList;
    }


    /**
     * java8之前对象集合根据某个字段分组
     */
    @Test
    public void sortUserListByAge(){
        List<User> userList = getUserList();
        Map<String, List<User>> groupByUserCityMap = new HashMap<>();
        for (User user : userList) {
            List<User> tmpList = groupByUserCityMap.get(user.getCity());
            if (tmpList == null) {
                tmpList = new ArrayList<>();
                tmpList.add(user);
                groupByUserCityMap.put(user.getCity(), tmpList);
            } else {
                tmpList.add(user);
            }
        }
        System.out.println("按照城市分组后结果："+groupByUserCityMap);
    }

    /**
     * java8根据某个字段分组
     */
    @Test
    public void java8GroupUserList(){
        List<User> userList = getUserList();
        Map<String, List<User>> groupByUserNameMap = userList.stream().collect(Collectors.groupingBy(User::getName));
        System.out.println("按照姓名分组后结果："+groupByUserNameMap);
    }
}
