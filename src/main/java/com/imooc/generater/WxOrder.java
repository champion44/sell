package com.imooc.generater;

public class WxOrder {
    private Integer id;

    private String storephone;

    private String custname;

    private String custphone;

    private String custaddr;

    private Integer productid;

    private Double orderfee;

    private Double buyfee;

    private Double mailfee;

    private Double totalfee;

    private Integer gettype;

    private Integer payflag;

    private String remake;

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

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname == null ? null : custname.trim();
    }

    public String getCustphone() {
        return custphone;
    }

    public void setCustphone(String custphone) {
        this.custphone = custphone == null ? null : custphone.trim();
    }

    public String getCustaddr() {
        return custaddr;
    }

    public void setCustaddr(String custaddr) {
        this.custaddr = custaddr == null ? null : custaddr.trim();
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public Double getOrderfee() {
        return orderfee;
    }

    public void setOrderfee(Double orderfee) {
        this.orderfee = orderfee;
    }

    public Double getBuyfee() {
        return buyfee;
    }

    public void setBuyfee(Double buyfee) {
        this.buyfee = buyfee;
    }

    public Double getMailfee() {
        return mailfee;
    }

    public void setMailfee(Double mailfee) {
        this.mailfee = mailfee;
    }

    public Double getTotalfee() {
        return totalfee;
    }

    public void setTotalfee(Double totalfee) {
        this.totalfee = totalfee;
    }

    public Integer getGettype() {
        return gettype;
    }

    public void setGettype(Integer gettype) {
        this.gettype = gettype;
    }

    public Integer getPayflag() {
        return payflag;
    }

    public void setPayflag(Integer payflag) {
        this.payflag = payflag;
    }

    public String getRemake() {
        return remake;
    }

    public void setRemake(String remake) {
        this.remake = remake == null ? null : remake.trim();
    }
}