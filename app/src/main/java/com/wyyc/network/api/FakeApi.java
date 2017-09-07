package com.wyyc.network.api;

import android.support.annotation.NonNull;

import com.wyyc.bean.FakeThing;
import com.wyyc.bean.FakeToken;

import java.util.Random;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2017/3/24.
 */
public class FakeApi {


    Random mRandom = new Random();


    /**
     * 模拟网络请求token的过程
     *
     * @param fakeAuth
     * @return
     */
    public Observable<FakeToken> getFakeToken(@NonNull String fakeAuth) {

        return Observable.just(fakeAuth).map(new Func1<String, FakeToken>() {
            @Override
            public FakeToken call(String fakeAuth) {
                // Add some random lantency to mock the network lantency
                int fakenCost = mRandom.nextInt(500) + 500;
                try {
                    Thread.sleep(fakenCost);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                FakeToken fakeToken = new FakeToken();
                fakeToken.token = createToken();

                return fakeToken;
            }
        });
    }

    private String createToken() {

        return "fake_token" + System.currentTimeMillis() % 10000;
    }


    public Observable<FakeThing> getFakeData(FakeToken fakeToken) {

        return Observable.just(fakeToken).map(new Func1<FakeToken, FakeThing>() {
            @Override
            public FakeThing call(FakeToken fakeToken) {
                //Add some random lantency to mock the network lantency
                int fakenCost = mRandom.nextInt(500) + 500;
                try {
                    Thread.sleep(fakenCost);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (fakeToken.expired) {
                    throw new IllegalArgumentException("Token expired");
                }

                FakeThing fakeThing = new FakeThing();
                fakeThing.id = (int) (System.currentTimeMillis() % 1000);
                fakeThing.name = "FAKE_USER" + fakeThing.id;
                return fakeThing;
            }
        });

    }


}
