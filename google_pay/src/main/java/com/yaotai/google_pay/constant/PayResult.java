package com.yaotai.google_pay.constant;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class PayResult {
    @IntDef({SERVICE_TIMEOUT,FEATURE_NOT_SUPPORTED,SERVICE_DISCONNECTED,OK,USER_CANCELED,SERVICE_UNAVAILABLE,BILLING_UNAVAILABLE,ITEM_UNAVAILABLE
    ,DEVELOPER_ERROR,ERROR,ITEM_ALREADY_OWNED,ITEM_NOT_OWNED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Code {
    }
    /** The request has reached the maximum timeout before Google Play responds. */
    public static final int SERVICE_TIMEOUT = -3;
    /** Requested feature is not supported by Play Store on the current device. */
    public static final int FEATURE_NOT_SUPPORTED = -2;
    /**
     * Play Store service is not connected now - potentially transient state.
     *
     * <p>E.g. Play Store could have been updated in the background while your app was still
     * running. So feel free to introduce your retry policy for such use case. It should lead to a
     * call to {@link #startConnection} right after or in some time after you received this code.
     */
    public static final int SERVICE_DISCONNECTED = -1;
    /**
     * Success
     */
    public static final int OK = 0;
    /**
     * User pressed back or canceled a dialog
     */
    public static final int USER_CANCELED = 1;
    /**
     * Network connection is down
     */
    public static final int SERVICE_UNAVAILABLE = 2;
    /**
     * Billing API version is not supported for the type requested
     */
    public static final int BILLING_UNAVAILABLE = 3;
    /**
     * Requested product is not available for purchase
     */
    public static final int ITEM_UNAVAILABLE = 4;
    /**
     * Invalid arguments provided to the API. This error can also indicate that the application was
     * not correctly signed or properly set up for In-app Billing in Google Play, or does not have
     * the necessary permissions in its manifest
     */
    public static final int DEVELOPER_ERROR = 5;
    /**
     * Fatal error during the API action
     */
    public static final int ERROR = 6;
    /**
     * Failure to purchase since item is already owned
     */
    public static final int ITEM_ALREADY_OWNED = 7;
    /**
     * Failure to consume since item is not owned
     */
    public static final int ITEM_NOT_OWNED = 8;
}
