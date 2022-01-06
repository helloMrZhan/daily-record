package com.zjq.dailyrecord.vo.ding;

import lombok.Data;

/**
 * <p>请求内容</p>
 *
 * @Author zjq
 * @Date 2022/1/6
 */
@Data
public class ReqBody {

    /**
     * 消息类型
     */
    private String msgtype;
    /**
     * 消息内容
     */
    private Content text;
    /**
     * 通知对象
     */
    private At at;
}
