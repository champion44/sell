package com.imooc.dataobject;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
@Data
@Entity
public class WxMiniUser {
	@Id
    private Integer id;

    private String userPhone;

    private String userOpenid;

    private String hostPhone;

    private String storeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    public String getUserOpenid() {
        return userOpenid;
    }

    public void setUserOpenid(String userOpenid) {
        this.userOpenid = userOpenid == null ? null : userOpenid.trim();
    }

    public String getHostPhone() {
        return hostPhone;
    }

    public void setHostPhone(String hostPhone) {
        this.hostPhone = hostPhone == null ? null : hostPhone.trim();
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId == null ? null : storeId.trim();
    }
}