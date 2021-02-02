package com.yaotai.google_pay.bean;

public class PayDbBean {
    private String orderId;
    private String params;

    public PayDbBean(String orderId, String params) {
        this.orderId = orderId;
        this.params = params;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "PayDbBean{" +
                "orderId='" + orderId + '\'' +
                ", params='" + params + '\'' +
                '}';
    }
}
