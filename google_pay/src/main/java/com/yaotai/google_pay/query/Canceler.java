package com.yaotai.google_pay.query;

/**
 * 取消器
 *
 * @author: moodd
 * @date: 2019/6/12 11:09
 */
public interface Canceler {
    /**
     * 取消
     */
    void cancel();

    /**
     *  是否取消
     * @return true：已取消 false:未取消
     */
    boolean isCanceled();
}
