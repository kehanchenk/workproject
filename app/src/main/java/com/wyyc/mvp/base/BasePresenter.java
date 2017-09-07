package com.wyyc.mvp.base;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2017/6/14.
 */
public class BasePresenter<T> {

    // View 的引用
    protected WeakReference<T> mViewRef;

    public void attachView(T view) {

        mViewRef = new WeakReference<>(view);
    }

    public void detachView() {

        if (mViewRef != null) {
            mViewRef.clear();
        }
    }

    protected T getView() {
        return mViewRef.get();
    }
}
