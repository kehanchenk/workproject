package com.wyyc.mymvpframe.ui.news;

import com.wyyc.mymvpframe.adapter.item.NewsInfo;
import com.wyyc.mymvpframe.ui.IBaseView;

import java.util.List;

/**
 * Created by zqq on 2017/6/20.
 */
public interface MyMvpFrameContract {

    interface View extends IBaseView {

        void updateView(List<NewsInfo> infos);
    }

//    interface PresenterI extends BasePresenter {
//
//        void requestData(String param);
//    }

}
