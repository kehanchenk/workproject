package com.wyyc.myview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by zqq on 2017/3/8.
 */
public class MyRecycleView extends RecyclerView {

    private View mView;


    private OnItemScrollListener mScrollListener;

    public void setScrollListener(OnItemScrollListener scrollListener) {
        mScrollListener = scrollListener;
    }

    public MyRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        mView = getChildAt(0);
        Log.e("zaa", "onLayout");//后执行
    }

    @Override
    public void onScrolled(int dx, int dy) {
        View childAt = getChildAt(0);

        if (mScrollListener != null) {

            //如果不是第一个View
            if (childAt != null && mView != childAt) {

                Log.e("zaa", "onScrolled");
                mView = childAt;
                mScrollListener.scroller(getChildAdapterPosition(mView));
            }

        }
    }
//    @Override
//    public boolean onTouchEvent(MotionEvent e) {
//        switch (e.getAction()) {
//            case MotionEvent.ACTION_MOVE:
//                mView = getChildAt(0);
//                if (mScrollListener != null) {
//                    mScrollListener.scroller(getChildAdapterPosition(mView));
//                }
//        }
//        return super.onTouchEvent(e);
//    }

    /**
     * 滚动回调接口
     */
    public interface OnItemScrollListener {

        void scroller(int position);

    }

}
