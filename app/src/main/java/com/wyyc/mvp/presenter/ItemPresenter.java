package com.wyyc.mvp.presenter;

import com.wyyc.bean.MvpBean;
import com.wyyc.mvp.model.IPriceModel;
import com.wyyc.mvp.model.PriceModelImpl;
import com.wyyc.mvp.view.ItemView;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by zqq on 2017/6/14.
 */
public class ItemPresenter<T extends ItemView> {

    static {
        String notes = "mvp的模式 在Presenter中持有 view 其实是 实现它的 Activity 的引用，长期没有释放存在内存泄漏问题";
        String method = "弱引用 软引用会在内存紧张的时候被回收，所以处理方式可以是使用弱引用持有view";
    }

    //作为 view 和 model 的桥梁   既然作为桥梁肯定是需要同 两方面交互，这个类不仅仅需要处理数据，加载进度条。
    //所以作为表示层 它必须要有视图层的引用 ，同时也是 model 的引用


    //View 的引用
    // 如何持有view 的引用？？？？ 通过构造方法提供实例化
//    ItemView mItemView;

    // 使用弱引用
    protected WeakReference<T> mViewRef;


    //model 的引用
    IPriceModel mModel = new PriceModelImpl();

    public ItemPresenter() {
//        mItemView = itemView;
//        this.mViewRef = new WeakReference<>(itemView);
    }

    //
    public void fetch() {
        if (mModel != null) {
            //显示进度条
            if (mViewRef.get() != null) {
                mViewRef.get().showLoading();
            }
            //加载数据
            mModel.loadItem(new IPriceModel.ItemOnLoadListener() {
                @Override
                public void onComplete(List<MvpBean> mvpBeen) {
                    //返回数据
                    //显示到View层
                    ItemView sitemView = mViewRef.get();
                    if (sitemView != null) {
                        mViewRef.get().showItem(mvpBeen);
                    }
                }
            });
        }
    }

    public void attachView(T view) {

        mViewRef = new WeakReference<>(view);
    }

    public void detachView() {

        if (mViewRef != null) {
            mViewRef.clear();
        }
    }

}
