package com.wyyc.zqqworkproject;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class BottomSheetActivity extends AppCompatActivity {


    private int currItem;
    private int select;

    private TextView text_wheel;
    private  TextView num_wheel;

    private ImageView arrow_down_one, arrow_down_two;
    private ObjectAnimator animationArrowDownOne, animationArrowDownTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet);
    }


}
