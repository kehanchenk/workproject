package com.wyyc.fragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wyyc.myview.adapter.MapListAdapter;
import com.wyyc.bean.Item;
import com.wyyc.function.GankBeanResultToItemMapper;
import com.wyyc.network.Network;
import com.wyyc.zqqworkproject.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zqq on 2017/3/22.
 */
public class MapFragment extends Fragment {

    @BindView(R.id.pageTv)
    TextView mPageTv;
    @BindView(R.id.gridRv)
    RecyclerView mGridRv;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;


    private Subscription mSubscription;

    private int page = 0;

    MapListAdapter mAdapter = new MapListAdapter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map, container, false);

        ButterKnife.bind(this, view);

        mGridRv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mGridRv.setAdapter(mAdapter);


        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        unsubscribe();
        loadimage(1);
    }

    private void unsubscribe() {

        if (mSubscription != null && mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    private void loadimage(int pages) {
        mSubscription=Network.getGankApi().getGankImage(10, pages)
                .map(GankBeanResultToItemMapper.getInstance()) //转换,将结果通过map直接转化成适配器中需要的数据
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Item>>() {

                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), "数据加载失败", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onNext(List<Item> items) {
                        mAdapter.setData(items);
                        mPageTv.setText(getString(R.string.page_with_number,page));
                    }
                });
    }
}
