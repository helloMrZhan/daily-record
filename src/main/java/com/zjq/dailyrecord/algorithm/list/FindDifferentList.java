package com.zjq.dailyrecord.algorithm.list;

import java.util.*;

/**
 * 使用Java最快捷的找出两个大量数据List集合的不同元素
 * 测试数据为集合A: 1千, 1万, 10万,1百万, 1千万的数据量.
 * 集合B比集合A多初始化六条数据，集合A添加一条特有的数据。
 * 测试数据使用空字符串 + 自然数的方式.
 * @author zjq
 * @date 2022-08-15
 */
public class FindDifferentList {


    public static void main(String[] args) {
        // javaAPI();
        // doubleFor();

        List<String> listA = dataList(1000000);
        //集合A添加一个集合B没有的元素
        listA.add("onlyA10086");
        List<String> listB = dataList(1000006);
        getDifferListByMapPlus(listA,listB);
        //getDifferListByMap(listA,listB);
    }


    /**
     * Java API方法比对
     */
    public static void javaAPI(){
        List<String> listA = dataList(10000);
        //集合A添加一个集合B没有的元素
        listA.add("onlyA10086");
        List<String> listB = dataList(10006);
        Long startTime = System.currentTimeMillis();
        // 复制集合A和集合B作为备份
        List<String> listABak = new ArrayList<>(listA);
        List<String> listBBak = new ArrayList<>(listB);
        // 集合B存在，集合A不存在的元素
        listB.removeAll(listA);
        // 集合A存在，集合B不存在的元素
        listA.removeAll(listBBak);
        Long endTime = System.currentTimeMillis();
        List<String> differentList = new ArrayList<>();
        differentList.addAll(listB);
        differentList.addAll(listA);
        System.out.println("集合A和集合B不同的元素："+differentList);
        Long costTime = endTime-startTime;
        System.out.println("Java API 比对耗时："+costTime+"毫秒。");

    }


    /**
     * 该方法实际上就是将removeAll的实现逻辑用自己的方式写出来
     * 所以执行效率,运行结果和上一种方法没什么区别,这里只贴代码出来,不再赘述
     */
    private static void doubleFor() {
        List<String> listA = dataList(1000);
        //集合A添加一个集合B没有的元素
        listA.add("onlyA10086");
        List<String> listB = dataList(1006);
        List<String> differentList = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        for (String str : listB) {
            if (!listA.contains(str)) {
                differentList.add(str);
            }
        }
        for (String str : listA) {
            if (!listB.contains(str)) {
                differentList.add(str);
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("集合A和集合B不同的元素："+differentList);
        System.out.println("使用双层遍历方法 比对耗时: " + (endTime - startTime)+"毫秒。");
    }

    /**
     * 借助Map来获取listA、listB的不同元素集合
     *
     * @param listA 集合A
     * @param listB 集合B
     * @return list<String> 不同元素集合
     */
    public static List<String> getDifferListByMap(List<String> listA, List<String> listB) {
        List<String> differList = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        long beginTime = System.currentTimeMillis();
        for (String strA : listA) {
            map.put(strA, 1);
        }
        for (String strB : listB) {
            Integer value = map.get(strB);
            if (value != null) {
                map.put(strB, ++value);
                continue;
            }
            map.put(strB, 1);
        }

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            //获取不同元素集合
            if (entry.getValue() == 1) {
                differList.add(entry.getKey());
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("集合A和集合B不同的元素："+differList);
        System.out.println("使用map方式遍历, 对比耗时: " + (endTime - beginTime)+"毫秒。");
        return differList;
    }

    /**
     * 找出两个集合中不同的元素
     *
     * @param collmax
     * @param collmin
     * @return
     */
    public static Collection getDifferListByMapPlus(Collection collmax, Collection collmin) {
        //使用LinkedList防止差异过大时,元素拷贝
        Collection csReturn = new LinkedList();
        Collection max = collmax;
        Collection min = collmin;
        long beginTime = System.currentTimeMillis();
        //先比较大小,这样会减少后续map的if判断次数
        if (collmax.size() < collmin.size()) {
            max = collmin;
            min = collmax;
        }
        //直接指定大小,防止再散列
        Map<Object, Integer> map = new HashMap<Object, Integer>(max.size());
        for (Object object : max) {
            map.put(object, 1);
        }
        for (Object object : min) {
            if (map.get(object) == null) {
                csReturn.add(object);
            } else {
                map.put(object, 2);
            }
        }
        for (Map.Entry<Object, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                csReturn.add(entry.getKey());
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("集合A和集合B不同的元素："+csReturn);
        System.out.println("使用map方式遍历, 对比耗时: " + (endTime - beginTime)+"毫秒。");
        return csReturn;
    }

    /**
     * 找出两个集合中相同的元素
     *
     * @param collmax
     * @param collmin
     * @return
     */
    public static Collection getSameListByMap(Collection collmax, Collection collmin) {
        //使用LinkedList防止差异过大时,元素拷贝
        Collection csReturn = new LinkedList();
        Collection max = collmax;
        Collection min = collmin;
        //先比较大小,这样会减少后续map的if判断次数
        if (collmax.size() < collmin.size()) {
            max = collmin;
            min = collmax;
        }
        //直接指定大小,防止再散列
        Map<Object, Integer> map = new HashMap<Object, Integer>(max.size());
        for (Object object : max) {
            map.put(object, 1);
        }
        for (Object object : min) {
            if (map.get(object) != null) {
                csReturn.add(object);
            }
        }
        return csReturn;
    }

    /**
     * 获取两个集合的不同元素,去除重复
     *
     * @param collmax
     * @param collmin
     * @return
     */
    public static Collection getDiffentNoDuplicate(Collection collmax, Collection collmin) {
        return new HashSet(getDifferListByMapPlus(collmax, collmin));
    }

    /**
     * 制造任意个元素的的List集合
     * @param size List集合的size
     * @return List<String>
     */
    private static List<String> dataList(int size) {
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            dataList.add("" + i);
        }
        return dataList;
    }



}
