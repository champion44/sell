package com.imooc.dataobject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
@Data
@Entity
public class WxUser {
	@Id
	@GeneratedValue
    private Integer id;

    private String storephone;

    private String storeid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStorephone() {
        return storephone;
    }

    public void setStorephone(String storephone) {
        this.storephone = storephone == null ? null : storephone.trim();
    }

    public String getStoreid() {
        return storeid;
    }

    public void setStoreid(String storeid) {
        this.storeid = storeid == null ? null : storeid.trim();
    }
}