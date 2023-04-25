package com.zjq.dailyrecord.algorithm.list;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zjq
 * @version 1.0
 * @date 2023/4/24 10:10
 */
public class ArrayToTreeMap {

  public static void main(String[] args) {
    Map<String,Map> topMap = new HashMap<>();
    //帮助map，帮助快速查找
    Map<String,Map> helpMap = new HashMap();

    String [][] arr = {{"A"},{"A","A2","A21"},{"A","A3","A31"},{"A","A3"} ,{"b","B1","B11"},{"D"},{"c","c1","c11"},{"d","d1","d11"},{"d","d1","d12","d1121"},{"d","d1","d11","d111"}};
    for (int i = 0; i <arr.length; i++) {
      String[] subArr = arr[i];
      for (int j = 0; j <subArr.length; j++){
        //
        String v = arr[i][j];
        //顶级节点，直接放入
        if (j==0){
          Map hasExist = helpMap.get(v);
          if (hasExist==null){
            HashMap<Object, Object> data = new HashMap<>();
            topMap.put(v,data);
            helpMap.put(v,data);
          }
        }else{
          Map hasExist = helpMap.get(v);
          if (hasExist==null){
            //父节点的key
            String parentKey = arr[i][j-1];
            Map existMap = helpMap.get(parentKey);
            Map data = new HashMap<>();
            existMap.put(v,data);
            //将自己也加入 helpMap
            helpMap.put(v,data);
            //非顶级节点，查询父节点
            //searchParent(parentKey,topMap);
          }

        }

      }
    }
    //
    System.out.println(JSON.toJSONString(topMap));

  }

  /**
   * 查询父节点
   */
  public static void searchParent(String parentKey,Map map){
    //map.entrySet()
  }

}
