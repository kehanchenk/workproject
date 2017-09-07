package com.wyyc.zqqworkproject;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

/**
 * 自定义view
 */
public class MyCoustomViewActivity extends AppCompatActivity {


    private TextView mTextView;
    private Chronometer mChronometer;
    private Button mButton;
    private Button mButton_s;
    private Button mButton_p;
    private long recordingTime = 0;// 记录下来的总时间

//    C:\Users\Administrator\AppData\Local\Android\Sdk

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_coustom_view);

        mTextView = (TextView) findViewById(R.id.tv_coutom_view);   //Ctrl+alt+F  改变全局变量;



        mChronometer = (Chronometer) findViewById(R.id.chro_time);
        mChronometer.setFormat("%s");

        mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                CharSequence text = chronometer.getText();
            }
        });

        mButton = (Button) findViewById(R.id.btn_stop);

        mButton_s = (Button) findViewById(R.id.btn_start);

        mButton_p = (Button) findViewById(R.id.btn_pause);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordingTime = 0;//重置
                mChronometer.setBase(SystemClock.elapsedRealtime());
            }
        });

        mButton_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//开始
                long s = 710408276;
                mChronometer.setBase(710408276 - recordingTime);// 跳过已经记录了的时间，起到继续计时的作用
                mChronometer.start();
            }
        });

        mButton_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//暂停
                mChronometer.stop();
                recordingTime = SystemClock.elapsedRealtime()
                               - mChronometer.getBase();// 保存这次记录了的时间


            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        Log.e("zaa", mTextView.getLeft() + "");
        Log.e("zaa", mTextView.getRight() + "");
        Log.e("zaa", mTextView.getTop() + "");
        Log.e("zaa", mTextView.getBottom() + "");
        Log.e("zaa", mTextView.getWidth() + "");
        Log.e("zaa", mTextView.getHeight() + "");
        Log.e("zaa", mTextView.getRotationX() + "");
        Log.e("zaa", mTextView.getRotationY() + "");
        Log.e("zaa", mTextView.getX() + "");
        Log.e("zaa", mTextView.getY() + "");
    }
}
