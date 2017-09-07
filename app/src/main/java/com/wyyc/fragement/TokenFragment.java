package com.wyyc.fragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wyyc.bean.FakeThing;
import com.wyyc.bean.FakeToken;
import com.wyyc.network.Network;
import com.wyyc.zqqworkproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/22.
 */
public class TokenFragment extends Fragment {

    @BindView(R.id.tokenTv)
    TextView mTokenTv;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private Subscription mSubscription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_token, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        unsubscribe();
        // 首先模拟请求网络获取token  然后通过token_auth 获取需要获取到内容
        mSubscription=Network.getFakeApi().getFakeToken("fake_auth_token")
                .flatMap(new Func1<FakeToken, Observable<FakeThing>>() {
                    @Override
                    public Observable<FakeThing> call(FakeToken fakeToken) {

                        return Network.getFakeApi().getFakeData(fakeToken);
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<FakeThing>() {
                    @Override
                    public void call(FakeThing thing) {
                        mTokenTv.setText(getString(R.string.get_data,thing.id,thing.name));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                        Toast.makeText(getActivity(), R.string.loading_failed,Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void unsubscribe() {
        if (mSubscription != null && mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

}
