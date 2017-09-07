package com.wyyc.fragement.CacheFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wyyc.myview.adapter.MapListAdapter;
import com.wyyc.bean.Item;
import com.wyyc.zqqworkproject.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.Subscription;

/**
 * Created by Administrator on 2017/3/24.
 */
public class CacheFragment extends Fragment {


    @BindView(R.id.loadingTimeTv)
    AppCompatTextView mLoadingTimeTv;
    @BindView(R.id.cacheRv)
    RecyclerView mCacheRv;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private long startingTime;

    private Subscription mSubscription;


    MapListAdapter adapter = new MapListAdapter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cache, container, false);
        ButterKnife.bind(this, view);
        mCacheRv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mCacheRv.setAdapter(adapter);
        return view;
    }

    @OnClick(R.id.clearMemoryCacheBt)
    void clearMemoryCache() {
        Data.getInstance().clearMemoryCache();
        adapter.setData(null);
        Toast.makeText(getActivity(), R.string.memory_cache_cleared, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.clearMemoryAndDiskCacheBt)
    void clearMemoryAndDiskCache() {
        Data.getInstance().clearMemoryAndDiskCache();
        adapter.setData(null);
        Toast.makeText(getActivity(), R.string.memory_and_disk_cache_cleared, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.loadBt)
    void load() {

        unsubscribe();
        Data.getInstance().subscribeData(new Observer<List<Item>>() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {
                Toast.makeText(getActivity(), R.string.loading_failed, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(List<Item> items) {
                int loadingTime = (int) (System.currentTimeMillis() - startingTime);
                mLoadingTimeTv.setText(getString(R.string.loading_time_and_source, loadingTime, Data.getInstance().getDataSourceText()));
                adapter.setData(items);
            }
        });


    }

    private void unsubscribe() {
        if (mSubscription != null && mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }

    }


}
