package com.zjq.dailyrecord.algorithm.list;

import com.zjq.dailyrecord.entity.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zjq
 * @date 2023/4/10 9:47
 * @description: List集合常见的操作和用法
 */
public class ListCommon {

    public static List<User> getUserList(){
        List<User> userList = new ArrayList<>();
        userList.add(new User("小明",11,"北京",66));
        userList.add(new User("小红",12,"上海",99));
        userList.add(new User("小詹",14,"广州",77));
        userList.add(new User("小龙",16,"深圳",55));
        userList.add(new User("小斯",9,"迈阿密",33));
        userList.add(new User("小布",12,"伦敦",88));
        return userList;
    }

    /**
     * Java8 把list集合中的某个属性通过逗号分隔生成字符串
     */
    @Test
    public void spitListJava8(){
        List<User> userList = getUserList();

        String names = userList.stream()
                // 获取每个User对象的名字属性
                .map(User::getName)
                // 用逗号分隔并合并成一个字符串
                .collect(Collectors.joining(", "));

        System.out.println(names);
    }


    /**
     * Java7 把list集合中的某个属性通过逗号分隔生成字符串
     */
    @Test
    public void spitListJava7(){
        List<User> userList = getUserList();

        StringBuilder sb = new StringBuilder();
        for(User user : userList){
            sb.append(user.getCity()).append(",");
        }

        String result = sb.toString();
        // 去掉最后一个逗号
        if(result.endsWith(",")){
            result = result.substring(0, result.length() - 1);
        }
        System.out.println(result);
    }



    @Test
    public void isSonList(){
        List<String> listA = new ArrayList<>();
        listA.add("b");
        listA.add("c");
        List<String> listB = new ArrayList<>();
        listB.add("c");
        listB.add("b");
        Boolean isSonList = listA.containsAll(listB);
        System.out.println("listB 是 listA 的子集："+ isSonList);
    }

    @Test
    public void isSonList2() throws Exception {
        List<String> creditCounterGuaranteeMeasures = Arrays.asList("02,03".split(","));
        List<String> schemeCounterGuaranteeMeasures = Arrays.asList("08,01".split(","));
        if(!schemeCounterGuaranteeMeasures.containsAll(creditCounterGuaranteeMeasures)){
            throw new Exception("反担保措施必须在产品方案的反担保措施范围内选择");
        }
    }

}
