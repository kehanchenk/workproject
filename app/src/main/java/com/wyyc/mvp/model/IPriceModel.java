package com.wyyc.mvp.model;

import com.wyyc.bean.MvpBean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/12.
 */
public interface IPriceModel {

    //加载适配器数据  一般开启线程加载 所以此处采用 callback 形式返回
    void loadItem(ItemOnLoadListener listener);


    //接口设计: 回调返回数据
    interface  ItemOnLoadListener{

        void onComplete(List<MvpBean> mvpBeen);

    }

}
