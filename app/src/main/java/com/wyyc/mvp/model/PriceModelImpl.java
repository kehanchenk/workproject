package com.wyyc.mvp.model;

import com.wyyc.bean.MvpBean;
import com.wyyc.zqqworkproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zqq on 2017/6/14.
 */
public class PriceModelImpl implements IPriceModel{


    @Override
    public void loadItem(ItemOnLoadListener listener) {


        //模拟网络访问
//        SystemClock.sleep(2000);

        //加载数据
        List mList = new ArrayList<>();
        MvpBean bean1 = new MvpBean("tom" + 1, R.drawable.ic_iamge1);
        MvpBean bean2 = new MvpBean("tom" + 2, R.drawable.ic_iamge2);
        MvpBean bean3 = new MvpBean("tom" + 3, R.drawable.ic_iamge3);
        MvpBean bean4 = new MvpBean("tom" + 4, R.drawable.ic_iamge4);
        MvpBean bean5 = new MvpBean("tom" + 5, R.drawable.ic_iamge5);
        MvpBean bean6 = new MvpBean("tom" + 1, R.drawable.ic_iamge1);
        MvpBean bean7 = new MvpBean("tom" + 2, R.drawable.ic_iamge2);
        MvpBean bean8 = new MvpBean("tom" + 3, R.drawable.ic_iamge3);
        mList.add(bean1);
        mList.add(bean2);
        mList.add(bean3);
        mList.add(bean4);
        mList.add(bean5);
        mList.add(bean6);
        mList.add(bean7);
        mList.add(bean8);

        //返回数据
        listener.onComplete(mList);

    }
}
