package com.wyyc.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.wyyc.zqqworkproject.R;

/**
 * Created by zqq on 2017/2/28.
 */
public class CustomControlBar extends View {


    private int mFirstColor;
    private int mWidth;//圈宽度
    private int mSecondColor;
    private Bitmap mBitmapSrc;
    private int mSplitSize;//每个相距的距离
    private int mCount;//总共的个数
    private int mCurrentCount;//当前位置

    private Paint mPaint;
    private Rect mRect;

    public CustomControlBar(Context context) {
        this(context, null);
    }

    public CustomControlBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomControlBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomControlBar, defStyleAttr, 0);

        mWidth = array.getDimensionPixelSize(R.styleable.CustomControlBar_barWidth, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 20, getResources().getDisplayMetrics()));

        mFirstColor = array.getColor(R.styleable.CustomControlBar_barFirstColor, Color.BLUE);

        mSecondColor = array.getColor(R.styleable.CustomControlBar_barSecondColor, Color.RED);

        mBitmapSrc = BitmapFactory.decodeResource(getResources(), array.getResourceId(R.styleable.CustomControlBar_bitmapSrc, R.drawable.ic_alipay));

        mSplitSize = array.getInt(R.styleable.CustomControlBar_barsplitSize, 20);

        mCount = array.getInt(R.styleable.CustomControlBar_barCount, 10);

        array.recycle();

        mPaint = new Paint();
        mRect = new Rect();


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);//设置线冒样式，取值有Cap.ROUND(圆形线冒)、Cap.SQUARE(方形线冒)、Paint.Cap.BUTT(无线冒)
        mPaint.setStyle(Paint.Style.STROKE);//仅描边
        int center = getWidth() / 2;//圆心坐标
        int radius = center - mWidth / 2;//半径
        /**
         * 划出圆环的位置
         */
        drawOval(canvas, center, radius);


        //通过图片的大小决定图片位置
        /**
         * 计算内切正方形的位置
         */
        int relRadius = radius - mWidth / 2; //内圆半径

        //计算内切正方形位置
        mRect.left = (int) ((relRadius - Math.sqrt(2) / 2 * 1.0f * relRadius) + mWidth);
        mRect.top = (int) ((relRadius - Math.sqrt(2) / 2 * 1.0f * relRadius) + mWidth);
        mRect.right = (int) (mRect.left + Math.sqrt(2) * relRadius);
        mRect.bottom = (int) (mRect.left + Math.sqrt(2) * relRadius);

        //如果图片小于内切正方形的宽。则将图片放在中心
        if (mBitmapSrc.getWidth() < Math.sqrt(2) * relRadius) {

            mRect.left = getWidth() / 2 - mBitmapSrc.getWidth() / 2;
            mRect.right = getWidth() / 2 + mBitmapSrc.getWidth() / 2;
            mRect.top = getHeight() / 2 - mBitmapSrc.getHeight() / 2;
            mRect.bottom = getHeight() / 2 + mBitmapSrc.getHeight() / 2;
        }
        canvas.drawBitmap(mBitmapSrc, null, mRect, mPaint);

    }

    private void drawOval(Canvas canvas, int center, int radius) {

        /**
         * 计算出出去间隙剩余的  得到每一块所占比例
         */
        float itemsize = (360 * 1.0f - mCount * mSplitSize) / mCount;//每一个圆块的宽

        RectF rectF = new RectF(center - radius, center - radius, center + radius, center + radius);//用于定义圆弧外圈的正方形

        mPaint.setColor(mFirstColor);
        for (int i = 0; i < mCount; i++) {
            canvas.drawArc(rectF, i * (itemsize + mSplitSize), itemsize, false, mPaint);
        }
        mPaint.setColor(mSecondColor);
        for (int i = 0; i < mCurrentCount; i++) {
            canvas.drawArc(rectF, i * (itemsize + mSplitSize), itemsize, false, mPaint);//根据进度画圆弧
        }
    }
    int xDown;
    int xUp;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDown = (int) event.getY();//相对于当前view的坐标
                break;
            case MotionEvent.ACTION_UP:
                xUp = (int) event.getY();
                if (xUp > xDown) {//下滑
                    mCurrentCount++;
                    invalidate();
                } else {
                    mCurrentCount--;
                    invalidate();
                }
        }
        return true;
    }
}
