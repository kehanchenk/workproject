package com.wyyc.mymvpframe.ui;

/**
 * Created by zqq on 2017/6/21.
 */
public abstract class BasePresenter<M, V extends IBaseView> implements IPresenter{

    protected M mModel;
    protected V mView;

    public BasePresenter(M m, V v) {
        this.mModel = m;
        this.mView = v;
    }
}
