package com.zjq.dailyrecord.utils.dingding;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.taobao.api.ApiException;
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

    public static void main(String[] args) throws ApiException {
        //把webhook设置成对应群的即可
        String webhook = "https://oapi.dingtalk.com/robot/send?access_token=xxx";
        String content = getContent();
        ArrayList<String> mobileList = Lists.newArrayList();
        mobileList.add("1768xxx66");
        sendMsgToGroupChat(webhook,false,mobileList,content);
        //sendMsgToGroupChatSDK(webhook);
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

    /**
     * 通知SDK消息发送到群聊
     * @param webhook 钉钉机器人地址(配置机器人的webhook)
     *
     */
    public static void sendMsgToGroupChatSDK(String webhook) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient(webhook);
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        //普通文本消息
        request.setMsgtype("text");
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        text.setContent("你好，测试文本消息");
        request.setText(text);
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        at.setAtMobiles(Arrays.asList("176xxx"));
        // isAtAll类型如果不为Boolean，请升级至最新SDK
        at.setIsAtAll(true);
        at.setAtUserIds(Arrays.asList("109929","32099"));
        request.setAt(at);

        //md格式消息
        request.setMsgtype("markdown");
        OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
        markdown.setTitle("杭州天气");
        markdown.setText("#### 杭州天气 @156xxxx8827\n" +
                "> 9度，西北风1级，空气良89，相对温度73%\n\n" +
                "> ![screenshot](https://gw.alicdn.com/tfs/TB1ut3xxbsrBKNjSZFpXXcXhFXa-846-786.png)\n"  +
                "> ###### 10点20分发布 [天气](http://www.thinkpage.cn/) \n");
        request.setMarkdown(markdown);
        //链接
        request.setMsgtype("link");
        OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
        link.setMessageUrl("https://blog.csdn.net/qq_35427589");
        link.setPicUrl("");
        link.setTitle("时代的火车向前开");
        link.setText("这个即将发布的新版本，创始人xx称它为红树林。而在此之前，每当面临重大升级，产品经理们都会取一个应景的代号，这一次，为什么是红树林");
        request.setLink(link);

        OapiRobotSendResponse response = client.execute(request);
        log.info("执行结果回执{}",JSON.toJSONString(response));
    }
}
