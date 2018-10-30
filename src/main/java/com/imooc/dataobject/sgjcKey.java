package com.imooc.dataobject;


import javax.persistence.Id;

import lombok.Data;
@Data
public class sgjcKey {
	@Id
    private Integer mdbh;
	@Id
    private String jcrq;
	@Id
    private String khxm;
	@Id
    private String sjh;
	@Id
    private String khh;

    
    public Integer getMdbh() {
        return mdbh;
    }

    public void setMdbh(Integer mdbh) {
        this.mdbh = mdbh;
    }

    public String getJcrq() {
        return jcrq;
    }

    public void setJcrq(String jcrq) {
        this.jcrq = jcrq == null ? null : jcrq.trim();
    }

    public String getKhxm() {
        return khxm;
    }

    public void setKhxm(String khxm) {
        this.khxm = khxm == null ? null : khxm.trim();
    }

    public String getSjh() {
        return sjh;
    }

    public void setSjh(String sjh) {
        this.sjh = sjh == null ? null : sjh.trim();
    }

    public String getKhh() {
        return khh;
    }

    public void setKhh(String khh) {
        this.khh = khh == null ? null : khh.trim();
    }

	public sgjcKey(Integer mdbh, String jcrq, String khxm, String sjh, String khh) {
		super();
		this.mdbh = mdbh;
		this.jcrq = jcrq;
		this.khxm = khxm;
		this.sjh = sjh;
		this.khh = khh;
	}
}