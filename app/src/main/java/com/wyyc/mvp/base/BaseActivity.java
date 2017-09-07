package com.wyyc.mvp.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wyyc.zqqworkproject.R;

/**
 * Created by Administrator on 2017/6/14.
 */
public abstract class BaseActivity<V,T extends BasePresenter<V>> extends AppCompatActivity {

    public T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp_test);
        mPresenter=createPresenter();
        mPresenter.attachView((V)this);
    }

    protected abstract T createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

}
