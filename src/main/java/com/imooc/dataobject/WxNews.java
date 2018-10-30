package com.imooc.dataobject;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class WxNews {
	@Id
    private Integer id;

    private String storeid;

    private String phonenum;

    private String indexpage;

    private String jumppage;

    private String remake1;

    private String remake2;

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

    public String getIndexpage() {
        return indexpage;
    }

    public void setIndexpage(String indexpage) {
        this.indexpage = indexpage == null ? null : indexpage.trim();
    }

    public String getJumppage() {
        return jumppage;
    }

    public void setJumppage(String jumppage) {
        this.jumppage = jumppage == null ? null : jumppage.trim();
    }

    public String getRemake1() {
        return remake1;
    }

    public void setRemake1(String remake1) {
        this.remake1 = remake1 == null ? null : remake1.trim();
    }

    public String getRemake2() {
        return remake2;
    }

    public void setRemake2(String remake2) {
        this.remake2 = remake2 == null ? null : remake2.trim();
    }
}