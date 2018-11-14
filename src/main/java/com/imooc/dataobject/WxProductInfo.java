package com.imooc.dataobject;

import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class WxProductInfo {
	@Id
    @GeneratedValue
    private BigInteger id;

    private String storeid;

    private String phonenum;

    private String productname;

    private String stars;

    private String brand;

    private String fullprice;

    private String saleprice;

    private String deposit;

    private String productinfo;

    private String indexpic;

    private String pic1;

    private String pic2;

    private String pic3;

    private String pic4;

    private String pic5;

    private String remark1;

    private String remark2;

  

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

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname == null ? null : productname.trim();
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars == null ? null : stars.trim();
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand == null ? null : brand.trim();
    }

    public String getFullprice() {
        return fullprice;
    }

    public void setFullprice(String fullprice) {
        this.fullprice = fullprice == null ? null : fullprice.trim();
    }

    public String getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(String saleprice) {
        this.saleprice = saleprice == null ? null : saleprice.trim();
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit == null ? null : deposit.trim();
    }

    public String getProductinfo() {
        return productinfo;
    }

    public void setProductinfo(String productinfo) {
        this.productinfo = productinfo == null ? null : productinfo.trim();
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1 == null ? null : remark1.trim();
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2 == null ? null : remark2.trim();
    }
}