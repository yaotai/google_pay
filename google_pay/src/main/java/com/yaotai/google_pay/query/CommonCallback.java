package com.yaotai.google_pay.query;

/**
 * @author: moodd
 * @date: 2019/6/10 10:07
 */
public interface CommonCallback<Result> {

    void call(Result result);

    interface CancelerCallback<Result> {

        void onStart(Canceler canceler);

        void onSuccess(Result result);

        void onFailure(Throwable e);

    }
}
