package com.wyyc.fragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wyyc.myview.adapter.MapListAdapter;
import com.wyyc.bean.ElemenntBean;
import com.wyyc.bean.Item;
import com.wyyc.function.GankBeanResultToItemMapper;
import com.wyyc.network.Network;
import com.wyyc.zqqworkproject.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/22.
 */
public class ZipFragment extends Fragment {


    @BindView(R.id.gridRv)
    RecyclerView mGridRv;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private Subscription mSubscription;

    MapListAdapter mAdapter = new MapListAdapter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_zip, container, false);
        ButterKnife.bind(this, view);

        mGridRv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mGridRv.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        unsubscirbe();
        loadAllImage();
    }
    private void unsubscirbe() {
        if (mSubscription != null && mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
    public void loadAllImage() {
        // 在app中存在调用不同接口的的情况，然后将结果糅合成统一格式数据
        Observable.zip(Network.getGankApi().getGankImage(10, 1).map(GankBeanResultToItemMapper.getInstance()),
                Network.getZqqApi().search("可爱"),
                new Func2<List<Item>, List<ElemenntBean>, List<Item>>() {
                    @Override
                    public List<Item> call(List<Item> items, List<ElemenntBean> been) {
                        List<Item> list = items;
                        for (int i = 0; i < been.size(); i++) {
                            Item item = new Item();
                            item.description = been.get(i).description;
                            item.imageUrl = been.get(i).image_url;
                            list.add(item);
                        }
                        return list;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Item>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(),"数据请求失败",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onNext(List<Item> items) {
                        mAdapter.setData(items);
                    }
                });

    }
}
