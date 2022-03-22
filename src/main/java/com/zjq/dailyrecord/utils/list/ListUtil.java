package com.zjq.dailyrecord.utils.list;

import com.zjq.dailyrecord.entity.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * List集合常见操作排序等
 * @author zjq
 * @date 2022/3/21
 */
public class ListUtil {

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

    public static List<Integer> getIntegerList(){
        List<Integer> integerList = new ArrayList<>();
        integerList.add(23);
        integerList.add(15);
        integerList.add(35);
        integerList.add(68);
        integerList.add(43);
        integerList.add(8);
        return integerList;
    }

    /**
     * Integer集合排序
     */
    @Test
    public void sortIntegerList(){
        List<Integer> integerList = getIntegerList();
        System.out.println("排序前："+integerList);
        //Integer本身有实现Comparable接口
        Collections.sort(integerList);
        System.out.println("排序后："+integerList);
    }

    /**
     * 对象集合根据某个字段排序
     */
    @Test
    public void sortUserListByAge(){
        List<User> userList = getUserList();
        System.out.println("排序前："+userList);
        //如果想直接通过Collections.sort(userList)排序，需要User实现Comparable接口，并重写compareTo方法
        Collections.sort(userList, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                //由小到大排序
                return o1.getAge() - o2.getAge();
                //由大到小排序
                //return o2.getAge() - o1.getAge();
            }
        });
        System.out.println("排序后："+userList);
    }

    /**
     * 对象集合根据多个字段排序
     */
    @Test
    public void sortUserList(){
        List<User> userList = getUserList();
        System.out.println("排序前："+userList);
        //如果想直接通过Collections.sort(userList)排序，需要User实现Comparable接口，并重写compareTo方法
        Collections.sort(userList, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                //先按照年龄排序
                int i = o1.getAge() - o2.getAge();
                if(i == 0){
                    //如果年龄相等再用分数进行排序
                    return o1.getScore() - o2.getScore();
                }
                return i;
            }
        });
        System.out.println("排序后："+userList);
    }
}
