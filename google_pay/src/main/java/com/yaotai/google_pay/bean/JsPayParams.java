package com.yaotai.google_pay.bean;

import java.io.Serializable;

/*
 * 编写：wenlong on 2018/3/5 15:44
 * 企业QQ： 2853239883
 * 钉钉：13430330686
 */
public class JsPayParams implements Serializable {

    public JsPayParams() {
    }

    /**
     * orderId : 234978615202352535683
     * uid : VSTA363238LWBLN
     * price : 1990
     * country : true
     * title : 7天一个月
     */

    private int id;
    private String type;
    private String orderId;
    private String uid;
    private int price;
    private String iap;
    private String title;

    private String productId;
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getIap() {
        return iap;
    }

    public void setIap(String iap) {
        this.iap = iap;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "JsPayParams{" +
                "country='" + iap + '\'' +
                ", type='" + type + '\'' +
                ", orderId='" + orderId + '\'' +
                ", uid='" + uid + '\'' +
                ", price=" + price +
                ", title='" + title + '\'' +
                ", productId='" + productId + '\'' +
                '}';
    }
}
