package com.wyyc.fragement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.wyyc.myview.adapter.ElementaryAdapter;
import com.wyyc.bean.ElemenntBean;
import com.wyyc.network.Network;
import com.wyyc.zqqworkproject.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ElementaryFragment extends Fragment {


    @BindView(R.id.gridRv)
    RecyclerView mGridRv;
//    @BindView(R.id.swipeRefreshLayout)
//    SwipeRefreshLayout mSwipeRefreshLayout;

    private Subscription mSubscription;

    ElementaryAdapter mAdapter = new ElementaryAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_elementary, container, false);
        ButterKnife.bind(this, view);

        mGridRv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mGridRv.setAdapter(mAdapter);
//        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED);
//        mSwipeRefreshLayout.setEnabled(false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        unsubscribe(); // 使用之前判断，防止内存泄漏
        search("可爱");
    }

    @OnCheckedChanged({R.id.searchRb1, R.id.searchRb2, R.id.searchRb3, R.id.searchRb4})
    void onTagChanged(RadioButton button, boolean checked) {
        if (checked) {
            //先清除数据
            mAdapter.setdata(null);
            search(button.getText().toString());
        }
    }

    private void search(String key) {
        //适用于后台取数据 ，前台使用的场景  * subscribeOn(): 指定 subscribe() 所发生的线程，即 Observable.OnSubscribe 被激活时所处的线程
        mSubscription=Network.getZqqApi()
                .search(key)
                .subscribeOn(Schedulers.io())  // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread())  // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Subscriber<List<ElemenntBean>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {

                        Toast.makeText(getActivity(),"数据加载失败",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onNext(List<ElemenntBean> been) {
                        mAdapter.setdata(been);
                    }
                });
    }

    private void unsubscribe() {

        if (mSubscription != null && mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unsubscribe();
    }
}
