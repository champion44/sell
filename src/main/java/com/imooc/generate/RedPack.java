package com.imooc.generate;

public class RedPack {
    private String redPackType;

    private Integer amount;

    private String time;

    private String openid;

    public String getRedPackType() {
        return redPackType;
    }

    public void setRedPackType(String redPackType) {
        this.redPackType = redPackType == null ? null : redPackType.trim();
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }
}