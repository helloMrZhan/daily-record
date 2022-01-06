package com.zjq.dailyrecord.utils.dingding;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zjq.dailyrecord.vo.ding.At;
import com.zjq.dailyrecord.vo.ding.Content;
import com.zjq.dailyrecord.vo.ding.ReqBody;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>钉钉通知工具类</p>
 *
 * @Author zjq
 * @Date 2022/1/6
 */
@Slf4j
public class DingDingUtil {

    /**
     * 通知消息发送到群聊
     * @param webhook 钉钉机器人地址(配置机器人的webhook)
     * @param isAtAll 是否通知所有人
     * @param mobileList 通知具体人的手机号码列表
     * @param content 消息内容
     */
    public static void sendMsgToGroupChat(String webhook,boolean isAtAll,List<String> mobileList,String content){
        try {
            //组装请求内容
            String reqStr = buildReqStr(content, isAtAll, mobileList);
            //推送消息(http请求)
            String result = HttpUtil.post(webhook, reqStr);
            log.info("通知响应结果：{}",result);
        }catch (Exception e){
            log.error("webhook通知失败",e);
        }
    }


    /**
     * 组装请求报文（Map封装）
     * @param content 通知内容
     * @param isAtAll 是否@所有人
     * @param mobileList 通知具体人的手机号码
     * @return
     */
    private static String buildReqStr(String content, boolean isAtAll, List mobileList) {

        //消息内容
        Map contentMap = Maps.newHashMap();
        contentMap.put("content", content);

        //通知人
        Map atMap = Maps.newHashMap();
        //1.是否通知所有人
        atMap.put("isAtAll", isAtAll);
        //2.通知具体人的手机号码列表
        atMap.put("atMobiles", mobileList);

        Map reqMap = Maps.newHashMap();
        reqMap.put("msgtype", "text");
        reqMap.put("text", contentMap);
        reqMap.put("at", atMap);

        return JSON.toJSONString(reqMap);

    }

    /**
     * 组装请求报文（实体封装）
     * @param content 通知内容
     * @param isAtAll 是否@所有人
     * @param mobileList 通知具体人的手机号码
     * @return
     */
    private static String buildReqStrVO(String content, boolean isAtAll, List mobileList) {

        //消息内容
        Content contentVO = new Content();
        contentVO.setContent(content);

        //通知人
        At atVO = new At();
        //1.是否通知所有人
        atVO.setIsAtAll(isAtAll);
        //2.通知具体人的手机号码列表
        atVO.setAtMobiles(mobileList);

        //请求内容
        ReqBody reqBody = new ReqBody();
        reqBody.setMsgtype("text");
        reqBody.setText(contentVO);
        reqBody.setAt(atVO);

        return JSON.toJSONString(reqBody);

    }

    public static void main(String[] args) {
        //把webhook设置成对应群的即可
        String webhook = "https://oapi.dingtalk.com/robot/send?access_token=xxx";
        String content = getContent();
        ArrayList<String> mobileList = Lists.newArrayList();
        sendMsgToGroupChat(webhook,false,mobileList,content);
    }

    /**
     * 获取通知消息
     * @return
     */
    private static String getContent() {
        //钉钉机器人消息内容
        String content;
        //通过转码网站http://tool.chinaz.com/Tools/unicode.aspx
        // 选择中文转Unicode把钉钉表情转换成unicode编码，也可以直接用表情对应的中文设置
        String milkyTea = "过来请我喝奶茶[奶茶][流鼻血][流鼻血]\u005b\u6d41\u9f3b\u8840\u005d";
        String NEWLINE = "\n";
        StringBuffer sb = new StringBuffer();
        sb.append("小哥哥，你好！")
                .append(NEWLINE)
                .append(milkyTea);
        content = sb.toString();
        return content;
    }

}
