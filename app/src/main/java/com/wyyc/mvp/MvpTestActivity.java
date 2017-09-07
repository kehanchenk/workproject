package com.wyyc.mvp;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.wyyc.bean.MvpBean;
import com.wyyc.mvp.adapter.ListAdapter;
import com.wyyc.mvp.base.BaseActivity;
import com.wyyc.mvp.presenter.ItemPresenter2;
import com.wyyc.mvp.view.ItemView;
import com.wyyc.zqqworkproject.R;

import java.util.List;

public class MvpTestActivity extends BaseActivity<ItemView,ItemPresenter2> implements ItemView {

    static {
        String descripiton = "在当前例子中，Activity代码不仅仅要 处理界面展示  同时还要处理数据，适配器的添加，监听。我们需要一个model 处理" +
                "原本的数据处理 展示。其中所需要的方法是根据实际逻辑使用提供的";
        String notes = "mvp的模式 因为";
    }

    RecyclerView mRecyclerView;
//    private ItemPresenter<ItemView> mPresenter;
//    private ItemPresenter2 mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp_test);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //逻辑处理全部通过它去处理
//        mPresenter = new ItemPresenter<>();
//        mPresenter=new ItemPresenter2();
//        mPresenter.attachView(this);
        mPresenter.fetch();

    }


    @Override
    public void showLoading() {
        Toast.makeText(this, "start load", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showItem(List<MvpBean> mvpBeen) {
        mRecyclerView.setAdapter(new ListAdapter(mvpBeen));
    }

    @Override
    protected ItemPresenter2 createPresenter() {
        return new ItemPresenter2();
    }



}
