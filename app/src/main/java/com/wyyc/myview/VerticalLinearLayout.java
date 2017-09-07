package com.wyyc.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

/**
 * Created by zqq on 2017/3/3.
 */
public class VerticalLinearLayout extends ViewGroup {

    /**
     * 屏幕高度
     */
    private int mScreenHeight;
    /**
     * 滑动辅助类
     */
    private Scroller mScroller;
    /**
     * 加速度检测
     */
    private VelocityTracker mVelocityTracker;
    /**
     * 手指按下时的getScrollX
     */
    private int mScrollStart;
    /**
     * 手指抬起时的getScrollY
     */
    private int mScrollEnd;
    /**
     * 记录移动时的Y
     */
    private int mLastY;
    /**
     * 是否正在滚动
     */
    private boolean isScrolling;
    /**
     * 记录当前页
     */
    private int currentPage = 0;

    private OnPageChangeListener mOnPageChangeListener;


    public VerticalLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        /**
         * 获取屏幕高度
         */
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics(); //屏幕指标
        wm.getDefaultDisplay().getMetrics(metrics);
        mScreenHeight = metrics.heightPixels;

        mScroller = new Scroller(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        /**
         * 绘制布局
         */
        if (changed) {

            int childCount = getChildCount();
            //设置布局参数  layoutParams  是告诉父容器如何放置自己的，它仅仅描述了view想要的宽高
            MarginLayoutParams params = (MarginLayoutParams) getLayoutParams();
            params.height = mScreenHeight * childCount;  //子view总共所占布局高度
            setLayoutParams(params);

            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                if (child.getVisibility() != View.GONE) {
                    child.layout(l, i * mScreenHeight, r, (i + 1) * mScreenHeight);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // 如果当前正在滚动，调用父类的onTouchEvent
        if (isScrolling) {
            return super.onTouchEvent(event);
        }
        int action = event.getAction();
        int y = (int) event.getY();//相对于view的坐标

        obtainVelocity(event);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mScrollStart = getScrollY(); //此处是当前view相对于Y轴的滑动的距离
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:

                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                int dy = mLastY - y;
                //边界检查
                int scrollY = getScrollY();
                if (dy < 0 && scrollY + dy < 0) {
                    dy = -scrollY;
                }
                // 已经到达底部，上拉多少，就往下滚动多少
                if (dy > 0 && scrollY + dy > getHeight() - mScreenHeight) {
                    dy = getHeight() - mScreenHeight - scrollY;
                }
                scrollBy(0, dy);
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                mScrollEnd = getScrollY();//滑动距离

                Log.e("zaa", "start" + mScrollStart);
                Log.e("zaa", "end" + mScrollEnd);
                int dScrollY = mScrollEnd - mScrollStart;

                if (wantScrollToNext())// 往上滑动
                {
                    if (shouldScrollToNext()) {
                        mScroller.startScroll(0, getScrollY(), 0, mScreenHeight - dScrollY);

                    } else {
                        mScroller.startScroll(0, getScrollY(), 0, -dScrollY);
                    }
                }

                if (wantScrollToPre())// 往下滑动
                {
                    if (shouldScrollToPre()) {
                        mScroller.startScroll(0, getScrollY(), 0, -mScreenHeight - dScrollY);

                    } else {
                        mScroller.startScroll(0, getScrollY(), 0, -dScrollY);
                    }
                }
                isScrolling = true;
                postInvalidate();
                recycleVelocity();
                break;
        }
        return true;
    }

    /**
     * 根据滚动距离判断是否能够滚动到下一页
     *
     * @return
     */
    private boolean shouldScrollToNext() {
        return mScrollEnd - mScrollStart > mScreenHeight / 2 || Math.abs(getVelocity()) > 600;//绝对值大于600
    }

    /**
     * 根据用户滑动，判断用户的意图是否是滚动到下一页
     *
     * @return
     */
    private boolean wantScrollToNext() {
        return mScrollEnd > mScrollStart;
    }

    /**
     * 根据滚动距离判断是否能够滚动到上一页
     *
     * @return
     */
    private boolean shouldScrollToPre() {
        return -mScrollEnd + mScrollStart > mScreenHeight / 2 || Math.abs(getVelocity()) > 600;
    }

    /**
     * 根据用户滑动，判断用户的意图是否是滚动到上一页
     *
     * @return
     */
    private boolean wantScrollToPre() {
        return mScrollEnd < mScrollStart;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {

            scrollTo(0, mScroller.getCurrY());
            postInvalidate();
        } else {

            int position = getScrollY() / mScreenHeight;

            Log.e("xxx", position + "," + currentPage);
            if (position != currentPage) {
                if (mOnPageChangeListener != null) {
                    currentPage = position;
                    mOnPageChangeListener.onPageChange(currentPage);
                }
            }



            isScrolling = false;
        }

    }

    /**
     * 获取y方向的加速度
     *
     * @return
     */
    private int getVelocity() {
        mVelocityTracker.computeCurrentVelocity(1000);
        return (int) mVelocityTracker.getYVelocity();
    }

    /**
     * 释放资源
     */
    private void recycleVelocity() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    /**
     * 初始化速度检测器
     *
     * @param event
     */
    private void obtainVelocity(MotionEvent event) {
        if (mVelocityTracker == null) {

            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);

    }

    /**
     * 设置回调接口
     *
     * @param onPageChangeListener
     */
    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        mOnPageChangeListener = onPageChangeListener;
    }

    /**
     * 回调接口
     *
     * @author zhy
     */
    public interface OnPageChangeListener {
        void onPageChange(int currentPage);
    }


}
