package com.wyyc.myview;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by zqq on 2017/9/5.
 * Description: 搜索动画  熟悉 PathMeasure 的使用
 */

public class SearchView extends View {

    private Paint mPaint;

    private Path mPath;
    private Path mLinePath;
    private Path mCirclePath;
    private Path mDst;
    private PathMeasure mPathMeasure;

    private int mCenterX;
    private int mCenterY;

    private float mValue;
    private float mValue2;
    private float mValue3;

    public SearchView(Context context) {
        super(context);
        init();
    }

    public SearchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(10);

        mPath = new Path();
        mLinePath = new Path();
        mDst = new Path();
        mCirclePath = new Path();

        mPathMeasure = new PathMeasure();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mCenterX = w / 2;
        mCenterY = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mDst.reset();

        canvas.drawColor(Color.GRAY);

        canvas.save();

        //第一步绘制开始搜索的静态图
        canvas.rotate(45, mCenterX, mCenterY);

        mPath.addCircle(mCenterX, mCenterY, 80, Path.Direction.CW);

        //绘制线条
        mLinePath.moveTo(mCenterX + 80, mCenterY);
        mLinePath.lineTo(mCenterX + 200, mCenterY);

        //绘制的第二部分的圆
        mCirclePath.addCircle(mCenterX, mCenterY, 200, Path.Direction.CW);

        //将路径添加至measure
        mPathMeasure.setPath(mPath, false);
        //获取路径长度
        float length = mPathMeasure.getLength();

        //获取圆的片段
        mPathMeasure.getSegment(length * mValue, length, mDst, true);

        canvas.drawPath(mDst, mPaint);

        //第二部分 绘制的直线
        if (mValue != 1) {
            canvas.drawPath(mLinePath, mPaint);
        } else {
            mPathMeasure.setPath(mLinePath, false);
            float len = mPathMeasure.getLength();
            mPathMeasure.getSegment(len * mValue2, len, mDst, true);
            canvas.drawPath(mDst, mPaint);
        }
        //第三部分 绘制大圆
        if (mValue2 == 1) {
            mPathMeasure.setPath(mCirclePath, false);
            float len = mPathMeasure.getLength();
            mPathMeasure.getSegment(len * mValue3, len * mValue3 + 50, mDst, true);
            canvas.drawPath(mDst, mPaint);
        }
        canvas.restore();
    }

    public void startAnim() {

        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(800);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                mValue = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });

        ValueAnimator animator1 = ValueAnimator.ofFloat(0, 1);
        animator1.setDuration(300);
        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mValue2 = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });

        ValueAnimator animator2 = ValueAnimator.ofFloat(0, 1);
        animator2.setDuration(1000);
        animator2.setRepeatCount(ValueAnimator.INFINITE);
        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mValue3 = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setStartDelay(1000);
        animatorSet.playSequentially(animator, animator1, animator2);  //依次执行
        animatorSet.start();


    }


}
