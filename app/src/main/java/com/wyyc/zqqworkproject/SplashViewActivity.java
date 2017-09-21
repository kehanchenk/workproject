package com.wyyc.zqqworkproject;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.wyyc.myview.SplashView;

public class SplashViewActivity extends AppCompatActivity {

    SplashView splashView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_splash_view);

        splashView = (SplashView) findViewById(R.id.splashview);
        //后台开始加载数据
        startLoadData();
    }

    Handler handler = new Handler();
    private void startLoadData() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //数据加载完毕，进入主界面--->开启后面的两个动画
                splashView.splashDisappear();
            }
        },3000);//延迟时间
    }
}
