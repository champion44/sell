package com.imooc.dataobject;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
@Data
@Entity
public class WxActivity {
	@Id
    private Integer id;

    private String storeid;

    private String phonenum;

    private String activityinfo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStoreid() {
        return storeid;
    }

    public void setStoreid(String storeid) {
        this.storeid = storeid == null ? null : storeid.trim();
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum == null ? null : phonenum.trim();
    }

    public String getActivityinfo() {
        return activityinfo;
    }

    public void setActivityinfo(String activityinfo) {
        this.activityinfo = activityinfo == null ? null : activityinfo.trim();
    }
}