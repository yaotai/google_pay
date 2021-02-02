/* Copyright (c) 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yaotai.google_pay.query;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents an in-app product's listing details.
 */
public class SkuDetails {
    String mItemType;
    String mSku;
    String mType;
    String mPrice;
    String mTitle;
    String mDescription;
    String mJson;

    String extPrice;
    String extCurrencySymbol;

    // TODO: 2019-08-22 读取google 支付
    String mprice_currency_code;
    String mToastPrice;

    public SkuDetails(String jsonSkuDetails) throws JSONException {
        this(IabHelper.ITEM_TYPE_INAPP, jsonSkuDetails);
    }

    public SkuDetails(String itemType, String jsonSkuDetails) throws JSONException {
        mItemType = itemType;
        mJson = jsonSkuDetails;
        JSONObject o = new JSONObject(mJson);
        mSku = o.optString("productId");
        mType = o.optString("type");
        mPrice = o.optString("price");
        mTitle = o.optString("title");
        mDescription = o.optString("description");
        mprice_currency_code = o.optString("price_currency_code");
        extPrice = getPrice(mPrice);
        extCurrencySymbol = getCurrencySymbol(mPrice, extPrice);
    }

    public String getToastPrice()
    {
        //Log.e("debugGooglePay", "查询到商品信息 mPrice= " + mPrice);
        int len = mPrice.length();
        for (int p= 0;p < len;p++)
        {
            if (mPrice.charAt(p) >=48 && mPrice.charAt(p)<=57)
            {
                return mPrice.substring(p,len);
            }
        }
        return mPrice;
    }

    public String getPrice_currency_code()
    {
        return mprice_currency_code;
    }

    public String getSku() {
        return mSku;
    }

    public String getType() {
        return mType;
    }

    public String getPrice() {
        return mPrice;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getExtPrice() {
        return extPrice;
    }

    public String getExtCurrencySymbol() {
        return extCurrencySymbol;
    }

    //"US$4.99"
    private static final Pattern pattern = Pattern.compile("\\d+\\.\\d+|\\d+");

    //截取数字
    private static String getPrice(String content) {
        try {
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                return matcher.group(0);
            }
        } catch (Exception e) {

        }
        return "";
    }

    private static String getCurrencySymbol(String content, String extPrice) {
        String symbol = null;
        try {
            int index = content.indexOf(extPrice);
            symbol = content.substring(index - 1, index);
        } catch (Exception e) {
        }
        return symbol;
    }

    @Override
    public String toString() {
        return "SkuDetails:" + mJson;
    }
}
