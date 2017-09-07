package com.wyyc.mvp.view;

import com.wyyc.bean.MvpBean;

import java.util.List;

/**
 * Created by zqq on 2017/6/14.
 * 视图层接口
 */
public interface ItemView {

    /**
     * 加载提示
     */
    void showLoading();

    /**
     * 回调显示数据
     * @param mvpBeen
     */
    void showItem(List<MvpBean> mvpBeen);

}
