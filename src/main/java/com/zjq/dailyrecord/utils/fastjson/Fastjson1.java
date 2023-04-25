package com.zjq.dailyrecord.utils.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;

/**
 * @author zjq
 * @date 2022/4/23 9:59
 * <p>title:Fastjson1使用</p>
 * <p>description:</p>
 */
@Slf4j
public class Fastjson1 {

    /**
     * json字符串转json对象
     */
    @Test
    public void strToJSONObject() {
        String jsonObjectStr = "{\"id\":\"1\",\"name\":\"张三\"}";
        JSONObject jsonObject = JSON.parseObject(jsonObjectStr);
        int id = jsonObject.getIntValue("id");
        String name = jsonObject.getString("name");
        log.info(id+" "+name);
    }

    /**
     * json数组字符串转json数组对象
     */
    @Test
    public void strArrayToJSONObject() {
        //普通数组
        String str = "[\"id\", 123]";
        JSONArray jsonArray1 = JSON.parseArray(str);
        String key = jsonArray1.getString(0);
        int value = jsonArray1.getIntValue(1);
        log.info(key+":"+value);
        //数组对象
        String jsonArrayObjectStr = "[{\"id\":\"1\",\"name\":\"张三\"},{\"id\":\"2\",\"name\":\"李四\"}]";
        JSONArray jsonArray = JSON.parseArray(jsonArrayObjectStr);
        JSONObject jsonObject = jsonArray.getJSONObject(1);
        int id = jsonObject.getIntValue("id");
        String name = jsonObject.getString("name");
        log.info(id+" "+name);
    }

    /**
     * JavaBean对象转JSON格式的字符串
     */
    @Test
    public void javaBeanToJSONStr() {
        User user = new User();
        user.setId(1);
        user.setName("小詹");
        String userStr = JSON.toJSONString(user);
        log.info(userStr);

        //JavaBean对象生成UTF8编码的byte[]
        byte[] utf8JSONBytes = JSON.toJSONBytes(user);
        log.info(new String(utf8JSONBytes));
    }

    /**
     * json字符串转JavaBean对象
     */
    @Test
    public void strToJavaBean() {
        String jsonObjectStr = "{\"id\":\"1\",\"name\":\"张三\"}";
        User user = JSON.parseObject(jsonObjectStr, User.class);
        log.info(user.toString());
    }


    /**
     * json字符串转JavaBean数组对象
     */
    @Test
    public void strToJavaBeanArray() {
        String jsonObjectStr = "[{\"id\":\"1\",\"name\":\"小詹\"},{\"id\":\"2\",\"name\":\"zjq\"}]";
        List<User> userList = JSON.parseArray(jsonObjectStr, User.class);
        log.info(userList.toString());
    }


    @Data
    static class User{
        private int id;
        private String name;
    }
}
