package com.wyyc.zqqworkproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.wyyc.myview.VerticalLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewGroup1Activity extends AppCompatActivity {

    @BindView(R.id.id_main_ly)
    VerticalLinearLayout mIdMainLy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_group1);
        ButterKnife.bind(this);

        mIdMainLy.setOnPageChangeListener(new VerticalLinearLayout.OnPageChangeListener() {
            @Override
            public void onPageChange(int currentPage) {
                Toast.makeText(ViewGroup1Activity.this, "第"+(currentPage+1)+"页", Toast.LENGTH_SHORT).show();
            }
        });
    }



}
