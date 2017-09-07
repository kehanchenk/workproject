package com.wyyc.myview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.wyyc.zqqworkproject.R;

/**
 * Created by zqq on 2017/9/4.
 * Description:
 */

public class MyWaveView extends View {

    private Paint mPaint;


    private Bitmap mBitMap;
    private PathMeasure mPathMeasure;
    private float[] pos;
    private float[] tan;
    private Matrix mMatrix;
    private float faction;
    private Path mPath;

    private static final int INT_WAVE_LE = 1000;
    private int mValue;

    public MyWaveView(Context context) {
        super(context);
        init();

    }

    private void init() {

        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);

        mPath = new Path();
        pos = new float[2];
        tan = new float[2];
        mMatrix = new Matrix();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize =  2;
        mBitMap = BitmapFactory.decodeResource(getResources(), R.drawable.timg,options);



    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPath.reset();

        int originY = 800;

        int waveWidth = INT_WAVE_LE / 2;


        /**
         * 绘制波浪
         */
        mPath.moveTo(-INT_WAVE_LE + mValue, originY);

        for (int i = -INT_WAVE_LE; i < getWidth() + INT_WAVE_LE; i += INT_WAVE_LE) {

            mPath.rQuadTo(waveWidth / 2, 60, waveWidth, 0);
            mPath.rQuadTo(waveWidth / 2, -60, waveWidth, 0);

        }

        mPath.lineTo(getWidth(), getHeight());
        mPath.lineTo(0, getHeight());
        mPath.close();

        canvas.drawPath(mPath, mPaint);


        mPathMeasure = new PathMeasure(mPath,false);
        float length = mPathMeasure.getLength();
        mMatrix.reset();

        /**
         * 获取路径该点的坐标值和正切值，，如果为true，则数据存入pos 和 tan
         */
        boolean posTan = mPathMeasure.getPosTan(length*faction,pos,tan);

        if (posTan) {
            /**
             * 改方法是获取得到的路径上某一个长度的位置，以及该位置的正切值的矩阵
             */
            mPathMeasure.getMatrix(length*faction, mMatrix, PathMeasure.TANGENT_MATRIX_FLAG | PathMeasure.POSITION_MATRIX_FLAG);
            mMatrix.preTranslate(- mBitMap.getWidth() / 2, - mBitMap.getHeight());
//            mMatrix.postTranslate(- mBitMap.getWidth() / 2, - mBitMap.getHeight());
            canvas.drawBitmap(mBitMap,mMatrix,mPaint);
        }


    }


    public void startAnim() {

//        ValueAnimator animator = ValueAnimator.ofInt(0, INT_WAVE_LE);
//        animator.setDuration(1000);
//        animator.setInterpolator(new LinearInterpolator());
//        animator.setRepeatCount(ValueAnimator.INFINITE);
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//
//                mValue = (int) animation.getAnimatedValue();
//                postInvalidate();
//            }
//        });
//
//        animator.start();

        ValueAnimator animators = ValueAnimator.ofFloat(0,1);
        animators.setDuration(20000);
        animators.setInterpolator(new LinearInterpolator());
        animators.setRepeatCount(ValueAnimator.INFINITE);
        animators.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                faction  = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animators.start();
    }
}
