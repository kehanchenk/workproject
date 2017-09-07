package com.wyyc.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by zqq on 2017/2/21.
 */
public class ScrollerLayout extends ViewGroup {


    private final Scroller mScroller;

    private int mMTouchSlop;  //系统认为是滑动的最小距离
    private float mXDown; //手指按下的屏幕坐标
    private float mXMove;  //手指移动的屏幕坐标
    private float mXLastMove; //上次触发ACTION_MOVE事件时的屏幕坐标

    /**
     * 界面可滚动的左边界
     */
    private int leftBorder;

    /**
     * 界面可滚动的右边界
     */
    private int rightBorder;


    public ScrollerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //第一步  创建Scroller实例
        mScroller = new Scroller(context);
        ViewConfiguration configuration = ViewConfiguration.get(context);

        mMTouchSlop = configuration.getScaledPagingTouchSlop();//获取最小滑动距离
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();//获得子View的个数
        for (int i = 0; i < childCount; i++) { //遍历所有子View
            View chileView = getChildAt(i);
            //为ScrollerLayout每一个子空间测量大小
            measureChild(chileView, widthMeasureSpec, heightMeasureSpec);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        if (changed) {
            int count = getChildCount();
            //count.fori
            for (int i = 0; i < count; i++) {
                View view = getChildAt(i);
                //为ScrollerLayout上每一个子空间都水平布局
                view.layout(i * view.getMeasuredWidth(), 0, (i + 1) * view.getMeasuredWidth(), view.getMeasuredHeight());
//                view.layout(0, i * view.getMeasuredHeight(), view.getMeasuredWidth(), (i + 1) * view.getMeasuredHeight());  垂直布局
            }

            // 初始化左右边界值
            leftBorder = getChildAt(0).getLeft();
            rightBorder = getChildAt(getChildCount() - 1).getRight();

        }
    }


    //重写事件拦截
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
                mXDown = ev.getRawX();//此为相对于屏幕的坐标  getX 是相对于view的坐标
                mXLastMove = mXDown;
                break;
            case MotionEvent.ACTION_MOVE:
                mXMove = ev.getRawX();
                float diff = Math.abs(mXMove - mXDown);//abs取绝对值
                //如果手指拖动的距离大于mMTouchSlop值时，说明应该进行滚动，拦截子控件的事件
                if (diff > mMTouchSlop) {
                    return true; //表示拦截事件
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mXMove = event.getRawX();
                int scrolledX = (int) (mXLastMove - mXMove);//滑动距离
                Log.e("zaa", "滑动距离" + scrolledX + "get" + getScaleX());
                Log.e("zaa", "滑动距离" + scrolledX + "get" + getWidth());
                if (getScrollX() + scrolledX < leftBorder) {  // 0
                    scrollTo(leftBorder, 0);
                    return true;
                } else if (getScrollX() + getWidth() + scrolledX > rightBorder) {  //3280
                    float a = getScaleX() + getWidth() + scrolledX;
                    scrollTo(rightBorder - getWidth(), 0);
                    return true;
                }
                scrollBy(scrolledX, 0);
                mXLastMove = mXMove;
                break;
            case MotionEvent.ACTION_UP:
                // 当手指抬起时，根据当前的滚动值来判定应该滚动到哪个子控件的界面
                int targetIndex = (getScrollX() + getWidth() / 2) / getWidth();
                int dx = targetIndex * getWidth() - getScrollX();
                // 第二步，调用startScroll()方法来初始化滚动数据并刷新界面
                mScroller.startScroll(getScrollX(), 0, dx, 0);//滑动起点坐标  滑动距离
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        //重写该方法，并在内部完成平滑滚动的逻辑
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}
