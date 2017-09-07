package com.wyyc.mymvpframe.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wyyc.App;
import com.wyyc.mymvpframe.injector.component.ApplicationComponent;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zqq on 2017/6/21.
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {

    private Unbinder mUnbinder;

    @Inject
    public T mPresenter;

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


    protected void startActivity(Class c) {
        this.startActivity(new Intent(this, c));
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(setLayoutRes());
        mUnbinder = ButterKnife.bind(this);
        initInjector(((App) getApplication()).getApplicationComponent());
        initView();
        updataView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != Unbinder.EMPTY) {

            mUnbinder.unbind();
        }
    }


}
