package com.wyyc.zqqworkproject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.wyyc.myview.LoadingView;
import com.wyyc.myview.SearchView;
import com.wyyc.myview.lovebezier.LoveLayout;

/**
 * 自定义view
 */
public class MyCoustomViewActivity extends AppCompatActivity {

    private LoadingView mView;
    private SearchView mSearchView;

    private LoveLayout mLoveLayout;


    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mLoveLayout.addLoveIcon();
            mHandler.sendEmptyMessageDelayed(1, 200);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_coustom_view);

        mView = (LoadingView) findViewById(R.id.loadingview);
        mSearchView = (SearchView) findViewById(R.id.searchview);

        mLoveLayout = (LoveLayout) findViewById(R.id.loveLayout);


        mHandler.sendEmptyMessage(1);


        mView.startAnim();
        mSearchView.startAnim();

    }


}
