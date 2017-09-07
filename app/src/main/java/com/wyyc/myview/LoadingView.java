package com.wyyc.myview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by zqq on 2017/9/4.
 * Description:
 */

public class LoadingView extends View {

    private Paint mPaint;

    private Path mPath;
    private Path mDst;

    private PathMeasure mPathMeasure;
    private float mLength;
    private float mAnimatorValue;


    public LoadingView(Context context) {
        super(context);
        init();
    }

    private void init() {

        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(15);

        mPath = new Path();
        //截取部分获取的路径
        mDst = new Path();

        //利用path 绘制圆
        mPath.addCircle(200, 200, 100, Path.Direction.CW);

        mPathMeasure = new PathMeasure();
        //将路径加入到 PathMeasure
        mPathMeasure.setPath(mPath, true);

//        mPathMeasure.getPosTan(); //获取路径上的坐标和切线坐标
        mLength = mPathMeasure.getLength();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mDst.reset();
        //硬件加速bug
        mDst.lineTo(0, 0);
        /**
         * 获取路径片段
         */
        float stop = mLength * mAnimatorValue;
        float start = (float) (stop - ((0.5 - Math.abs(mAnimatorValue - 0.5)) * mLength));
        //利用属性动画截取片段，同时利用属性动画完成重绘 mDst 是如果截取片段成功，则将截取的片段加入到mDest
        mPathMeasure.getSegment(start, stop, mDst, true);
        canvas.drawPath(mDst, mPaint);
    }

    /**
     * 开始动画
     */
    public void startAnim() {

        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(1000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatorValue = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();

    }
}
