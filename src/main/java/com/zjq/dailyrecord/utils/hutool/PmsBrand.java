package com.zjq.dailyrecord.utils.hutool;

import lombok.Data;

/**
 * @author 共饮一杯无
 * @date 2024/7/29 18:23
 * @description:
 */
public class PmsBrand {

    private Long id;

    private String name;

    private Integer showStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(Integer showStatus) {
        this.showStatus = showStatus;
    }
}
