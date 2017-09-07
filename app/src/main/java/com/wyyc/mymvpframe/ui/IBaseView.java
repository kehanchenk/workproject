package com.wyyc.mymvpframe.ui;

/**
 * Created by Administrator on 2017/6/21.
 */
public interface IBaseView {

    void showLoading();
    void dismiss();
    void showError(String message);

}
