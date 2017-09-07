package com.wyyc.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by zqq on 2017/3/2.
 */
public class ViewDrawLayout extends LinearLayout {


    private ViewDragHelper mViewDragHelper;
    private View mDragView;
    private View mAuToBackView;
    private View mEdgeTrcakerView;

    private Point mAutoBackOriginPos = new Point();

    public ViewDrawLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {

            @Override
            public boolean tryCaptureView(View child, int pointerId) {

                return true;
            }

            //在该方法中对child移动的边界进行控制
            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                /**
                 * 边界保护
                 * 第二个参数是将要移动的位置，如果只是在view内部移动
                 * 即：最小>=paddingleft  最大<=ViewGroup.getWidth()-paddingright-child.getwidth()
                 */
                int paddingLeft = getPaddingLeft();
                int rightbound = getWidth() - paddingLeft - child.getWidth();

                int min = Math.min(Math.max(left, paddingLeft), rightbound);

                return min;
            }

            //在该方法中对child移动的边界进行控制
            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return top;
            }

            /**
             * 手指释放后回调
             * @param releasedChild
             * @param xvel
             * @param yvel
             */
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {

                if (releasedChild == mAuToBackView) {

                    mViewDragHelper.settleCapturedViewAt(mAutoBackOriginPos.x, mAutoBackOriginPos.y);
                    invalidate();
                }
            }
        });

    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);


    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

        //获取初始位置
        mAutoBackOriginPos.x = mAuToBackView.getLeft();
        mAutoBackOriginPos.y = mAuToBackView.getTop();
    }

    /**
     * 直接把拦截事件处理交给ViewDragHelper
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    /**
     * 处理拦截事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    /**
     * 该方法是view从xml文件解析结束后调用的
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDragView = getChildAt(0);
        mAuToBackView = getChildAt(1);
        mEdgeTrcakerView = getChildAt(2);
    }
}
