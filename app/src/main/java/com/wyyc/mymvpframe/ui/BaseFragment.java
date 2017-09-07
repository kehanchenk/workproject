package com.wyyc.mymvpframe.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wyyc.App;
import com.wyyc.mymvpframe.injector.component.ApplicationComponent;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zqq on 2017/6/21.
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment {


    private Unbinder mUnbinder;

    private View mRootView;

    @Inject
    T mPresenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRootView = inflater.inflate(setLayoutRes(), container, false);

        mUnbinder = ButterKnife.bind(this, mRootView);

        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //注入
        initInjector(((App)getActivity().getApplication()).getApplicationComponent());

        initView();
        updataView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(mUnbinder !=Unbinder.EMPTY){

            mUnbinder.unbind();
        }

    }

    /**
     * 绑定布局文件
     *
     * @return
     */
    public abstract int setLayoutRes();

    /**
     * 提供注入操作并且提供applicationComponent对象
     *
     * @param component
     */
    public abstract void initInjector(ApplicationComponent component);


    /**
     * view初始化
     */
    public abstract void initView();

    /**
     * view视图更新
     */
    public abstract void updataView();

}
