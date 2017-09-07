package com.wyyc.fragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wyyc.bean.FakeThing;
import com.wyyc.bean.FakeToken;
import com.wyyc.network.Network;
import com.wyyc.network.api.FakeApi;
import com.wyyc.zqqworkproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by zqq on 2017/3/24.
 */
public class TokenAdvancedFragment extends Fragment {


    @BindView(R.id.tokenTv)
    TextView mTokenTv;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    boolean tokenUpdated;
    private Subscription mSubscription;


    FakeToken mToken = new FakeToken(true);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_token_advanced, container, false);

        ButterKnife.bind(this, view);
        return view;

    }


    @OnClick(R.id.invalidateTokenBt)
    void delete() {
        mToken.expired = true;
        Toast.makeText(getActivity(),R.string.token_destroyed,Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.requestBt)
    void request() {
        unsubscribe();
        tokenUpdated = false;
        Log.e("zaa", "0");
        //执行顺序 0216  重新获取token  02134516
        //由于大多数的token 不是一次性的，而是可以多次使用的 所以知道它被销毁之前或者超时  所哟使用过程中
        //需要保存token  发现当前token失效的时候自动获取新的token  并且访问之前请求由于token失效请求失败的接口
        final FakeApi fakeApi = Network.getFakeApi();
        //首先通过token取值，，如果过时或者为null 则重新请求
        mSubscription=Observable.just(mToken.token).flatMap(new Func1<String, Observable<FakeThing>>() {
            @Override
            public Observable<FakeThing> call(String s) {

                Log.e("zaa", "1");  //此处如果是空指针，则会调用retryWhen中的回调 捕获异常后重新获取token, 但是开始执行，回调已经准备就绪
                return mToken.token == null ? Observable.<FakeThing>error(new NullPointerException("Token is null"))
                        : fakeApi.getFakeData(mToken);
            }
            // 通过retryWhen 实现请求
        }).retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
            @Override
            public Observable<?> call(Observable<? extends Throwable> observable) {
                Log.e("zaa", "2");
                return observable.flatMap(new Func1<Throwable, Observable<?>>() {
                    @Override
                    public Observable<?> call(Throwable throwable) {
                        Log.e("zaa", "3");
                        if (throwable instanceof IllegalArgumentException || throwable instanceof NullPointerException) {
                            Log.e("zaa", "4");
                            return fakeApi.getFakeToken("fake_auth_code")
                                    .doOnNext(new Action1<FakeToken>() {
                                        @Override
                                        public void call(FakeToken fakeToken) {
                                            Log.e("zaa", "5");
                                            tokenUpdated = true;
                                            mToken.token = fakeToken.token;
                                            mToken.expired = fakeToken.expired;
                                        }
                                    });
                        }
                        return Observable.error(throwable);
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<FakeThing>() {
                    @Override
                    public void call(FakeThing thing) {

                        Log.e("zaa", "6");
                        String token = mToken.token;
                        if (tokenUpdated) {
                            token += "(" + getString(R.string.updated) + ")";
                        }
                        mTokenTv.setText(getString(R.string.get_token_and_data, token, thing.id, thing.name));

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toast.makeText(getActivity(),R.string.loading_failed,Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public void unsubscribe() {

        if (mSubscription != null && mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();

        }
    }
}
