package com.imooc.dataobject;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
@Entity
@Data
public class Khxx {
	@Id
    private Long khbh;

    private String sjh;

    private String khxm;

    private String khxb;

    private String khsr;

    private String khzy;

    private String khqq;

    private String khwx;

    private String khxj;

    private BigDecimal ljxf;

    private Long ljjf;

    private BigDecimal zkl;

    private String khdz;

    private String djrq;

    private String khh;

    private Integer mdbh;

    public Long getKhbh() {
        return khbh;
    }

    public void setKhbh(Long khbh) {
        this.khbh = khbh;
    }

    public String getSjh() {
        return sjh;
    }

    public void setSjh(String sjh) {
        this.sjh = sjh == null ? null : sjh.trim();
    }

    public String getKhxm() {
        return khxm;
    }

    public void setKhxm(String khxm) {
        this.khxm = khxm == null ? null : khxm.trim();
    }

    public String getKhxb() {
        return khxb;
    }

    public void setKhxb(String khxb) {
        this.khxb = khxb == null ? null : khxb.trim();
    }

    public String getKhsr() {
        return khsr;
    }

    public void setKhsr(String khsr) {
        this.khsr = khsr == null ? null : khsr.trim();
    }

    public String getKhzy() {
        return khzy;
    }

    public void setKhzy(String khzy) {
        this.khzy = khzy == null ? null : khzy.trim();
    }

    public String getKhqq() {
        return khqq;
    }

    public void setKhqq(String khqq) {
        this.khqq = khqq == null ? null : khqq.trim();
    }

    public String getKhwx() {
        return khwx;
    }

    public void setKhwx(String khwx) {
        this.khwx = khwx == null ? null : khwx.trim();
    }

    public String getKhxj() {
        return khxj;
    }

    public void setKhxj(String khxj) {
        this.khxj = khxj == null ? null : khxj.trim();
    }

    public BigDecimal getLjxf() {
        return ljxf;
    }

    public void setLjxf(BigDecimal ljxf) {
        this.ljxf = ljxf;
    }

    public Long getLjjf() {
        return ljjf;
    }

    public void setLjjf(Long ljjf) {
        this.ljjf = ljjf;
    }

    public BigDecimal getZkl() {
        return zkl;
    }

    public void setZkl(BigDecimal zkl) {
        this.zkl = zkl;
    }

    public String getKhdz() {
        return khdz;
    }

    public void setKhdz(String khdz) {
        this.khdz = khdz == null ? null : khdz.trim();
    }

    public String getDjrq() {
        return djrq;
    }

    public void setDjrq(String djrq) {
        this.djrq = djrq == null ? null : djrq.trim();
    }

    public String getKhh() {
        return khh;
    }

    public void setKhh(String khh) {
        this.khh = khh == null ? null : khh.trim();
    }

    public Integer getMdbh() {
        return mdbh;
    }

    public void setMdbh(Integer mdbh) {
        this.mdbh = mdbh;
    }
}