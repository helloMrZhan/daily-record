package com.zjq.dailyrecord.utils.list;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * List集合去除重复数据
 *
 * @author zjq
 * @date 2022/3/21
 */
public class ListRemoveRepeatTest {

    public static List<String> getUserList() {
        List<String> userList = new ArrayList<>();
        userList.add("小王");
        userList.add("小张");
        userList.add("小詹");
        userList.add("小王");
        userList.add("老王");
        userList.add("小詹");
        return userList;
    }

    /**
     * 通过HashSet去重（不保证顺序）
     * 利用了Set的特性：元素不可重复，其底层原理是先计算每个对象的hash值，再比较元素值是否相同，如果相同，则保留最新的。
     */
    @Test
    public void removeRepeat1() {
        List<String> userList = getUserList();
        System.out.println("去重前：" + userList);
        Set<String> hashSet = new HashSet<>(userList);
        List newList = new ArrayList(hashSet);
        System.out.println("去重后：" + newList);
    }

    /**
     * 通过HashSet去重（保证顺序）
     */
    @Test
    public void removeRepeat2() {
        List<String> userList = getUserList();
        System.out.println("去重前：" + userList);
        Set set = new HashSet();
        List newList = new ArrayList();
        for (String str : userList) {
            if (set.add(str)) {
                newList.add(str);
            }
        }
        System.out.println("去重后：" + newList);
    }

    /**
     * 遍历后判断赋给另一个list集合去重（保证顺序）
     */
    @Test
    public void removeRepeat3() {
        List<String> userList = getUserList();
        System.out.println("去重前：" + userList);
        List<String> newList = new ArrayList<String>();
        for (String str : userList) {
            if (!newList.contains(str)) {
                newList.add(str);
            }
        }
        System.out.println("去重后：" + newList);
    }

    /**
     * 通过TreeSet去重（保证顺序）
     * TreeSet集合实际上是利用TreeMap的带有一个比较器参数的构造方法实现，看JDK源码很清晰，最重要的是这个参数Comparator接口
     */
    @Test
    public void removeRepeat4() {
        List<String> userList = getUserList();
        System.out.println("去重前：" + userList);
        Set<String> treeSet = new TreeSet<>();
        treeSet.addAll(userList);
        List newList = new ArrayList(treeSet);
        System.out.println("去重后：" + newList);
    }

    /**
     * Java8中Stream流处理（保证顺序）
     * 首先获得此list的Stream，然后调用distinct()方法。Java8中提供流的方式对数据进行处理，非常快，底层用的是forkJoin框架，
     * 提供了并行处理，使得多个处理器同时处理流中的数据，所以耗时非常短。
     */
    @Test
    public void removeRepeat5() {
        List<String> userList = getUserList();
        System.out.println("去重前：" + userList);
        List newList = userList.stream().distinct().collect(Collectors.toList());
        System.out.println("去重后：" + newList);
    }
}
