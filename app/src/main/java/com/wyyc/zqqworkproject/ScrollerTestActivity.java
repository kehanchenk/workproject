package com.wyyc.zqqworkproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

public class ScrollerTestActivity extends AppCompatActivity {


//    @BindView(R.id.btn_scroller_to)
//    Button mBtnScrollerTo;
//    @BindView(R.id.btn_scroller_by)
//    Button mBtnScrollerBy;

    static {
        String message = "View自身就能通过ScrollerTo()和ScrollerBy()进行移动，但是Scroller其实是对View内容的移动。比如栗子里就是在移动button的文字";
        String message1 = "scrollTo()方法是让View相对于初始的位置滚动某段距离,scrollBy()方法则是让View相对于当前的位置滚动某段距离,Scroller内部调用了SrcollerBy()";
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller_test);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

//    @OnClick({R.id.btn_scroller_to, R.id.btn_scroller_by})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.btn_scroller_to:
//                mBtnScrollerTo.scrollTo(0,10);
//                break;
//            case R.id.btn_scroller_by:
//                mBtnScrollerBy.scrollBy(0,10);//当前位置的移动是和坐标相反，此为向上滑动10
//                break;
//        }
//    }
}
