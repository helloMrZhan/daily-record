package com.zjq.dailyrecord.vo.ding;

import lombok.Data;

import java.util.List;

/**
 * <p>@对象设置</p>
 *
 * @Author zjq
 * @Date 2022/1/6
 */
@Data
public class At {
    /**
     * 是否通知所有人
     */
    private Boolean isAtAll;
    /**
     * 通知具体人的手机号码
     */
    private List<String> atMobiles;
}
