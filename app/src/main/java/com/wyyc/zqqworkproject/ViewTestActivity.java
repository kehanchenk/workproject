package com.wyyc.zqqworkproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.wyyc.myview.ChinaMapView;
import com.wyyc.myview.LoadingView;
import com.wyyc.myview.SearchView;

public class ViewTestActivity extends AppCompatActivity {

    private LoadingView mView;
    private SearchView mSearchView ;

    private TextView mTextView;

    ChinaMapView mMapView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_test);

        mTextView = (TextView) findViewById(R.id.tv_content);

        mMapView = (ChinaMapView) findViewById(R.id.china);
        mMapView.setListener(new ChinaMapView.onClickListener() {
            @Override
            public void onClick(String s) {
                mTextView.setText("您当前点击的是"+s);
            }
        });

//        setContentView(new MyWaveView(this));
//        mView = new LoadingView(this);
//        mSearchView = new SearchView(this);
//        setContentView(mSearchView);
//        mSearchView.startAnim();
    }

}
