package com.wyyc.mymvpframe.ui.news;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.wyyc.mymvpframe.adapter.NewsAdapter;
import com.wyyc.mymvpframe.adapter.item.NewsInfo;
import com.wyyc.mymvpframe.injector.component.ApplicationComponent;
import com.wyyc.mymvpframe.injector.component.DaggerMyMvpFrameComponent;
import com.wyyc.mymvpframe.injector.module.MyMvpFrameModule;
import com.wyyc.mymvpframe.ui.BaseActivity;
import com.wyyc.zqqworkproject.R;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 基于dagge2 rxjava retrofit 的 mvp 框架设计流程
 */
public class MyMvpFrameActivity extends BaseActivity<MyMvpFramepresenter> implements MyMvpFrameContract.View {

    @BindView(R.id.Rv_mvp_frame)
    RecyclerView mRvMvpFrame;

    @Inject
    NewsAdapter mNewsAdapter;

    @Inject
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setLayoutRes() {
        return R.layout.activity_my_mvp_frame;
    }

    @Override
    public void initInjector(ApplicationComponent component) {
        DaggerMyMvpFrameComponent.builder().applicationComponent(component)
                .myMvpFrameModule(new MyMvpFrameModule(this))
                .build().inject(this);
    }

    @Override
    public void initView() {
        mRvMvpFrame.setLayoutManager(new LinearLayoutManager(this));
        mRvMvpFrame.setItemAnimator(new DefaultItemAnimator());
        mRvMvpFrame.setAdapter(mNewsAdapter);
    }

    @Override
    public void updataView() {
        mPresenter.requestData("可爱");
    }


    @Override
    public void showLoading() {
        mProgressDialog.show();

    }

    @Override
    public void dismiss() {
        mProgressDialog.dismiss();
    }

    @Override
    public void updateView(List<NewsInfo> infos) {

        mNewsAdapter.setData(infos);

    }

    @Override
    public void showError(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

}
