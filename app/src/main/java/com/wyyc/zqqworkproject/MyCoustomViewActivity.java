package com.wyyc.zqqworkproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wyyc.myview.LoadingView;
import com.wyyc.myview.SearchView;

/**
 * 自定义view
 */
public class MyCoustomViewActivity extends AppCompatActivity {

    private LoadingView mView;
    private SearchView mSearchView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_coustom_view);

        mView = (LoadingView) findViewById(R.id.loadingview);
        mSearchView = (SearchView) findViewById(R.id.searchview);
        mView.startAnim();
        mSearchView.startAnim();

    }


}
