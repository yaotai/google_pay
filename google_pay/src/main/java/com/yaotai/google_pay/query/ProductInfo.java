package com.yaotai.google_pay.query;

import java.io.Serializable;

/**
 * @author: moodd
 * @date: 2019/6/10 10:58
 */
public class ProductInfo implements Serializable, Cloneable {
    /**
     * 产品ID
     */
    private String productID;
    /**
     * 本地化的一个标题
     */
    private String localizedTitle;
    /**
     * 描述
     */
    private String localizedDescription;
    /**
     * 多少钱
     */
    private String price;
    /**
     * 货币符号
     */
    private String currencySymbol;


    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getLocalizedTitle() {
        return localizedTitle;
    }

    public void setLocalizedTitle(String localizedTitle) {
        this.localizedTitle = localizedTitle;
    }

    public String getLocalizedDescription() {
        return localizedDescription;
    }

    public void setLocalizedDescription(String localizedDescription) {
        this.localizedDescription = localizedDescription;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    @Override
    public String toString() {
        return "ProductInfo{" +
                "productID='" + productID + '\'' +
                ", localizedTitle='" + localizedTitle + '\'' +
                ", localizedDescription='" + localizedDescription + '\'' +
                ", price='" + price + '\'' +
                ", currencySymbol='" + currencySymbol + '\'' +
                '}';
    }
}
