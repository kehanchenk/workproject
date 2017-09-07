package com.wyyc.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import com.wyyc.zqqworkproject.R;

/**
 * Created by zqq on 2017/2/28.
 */
public class CustomProgress extends View {


    private int mFirstColor;
    private int mSecondColor;
    private int mOvalWidth;
    private int mSpeed;
    private int mProgress;


    private Paint mPaint;

    public CustomProgress(Context context) {
        this(context, null);
    }

    public CustomProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        /**
         * 通过以下方式获取自定义属性
         */
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomProgress, defStyleAttr, 0);
        /**
         * 获取各个属性值，，第二个参数是默认值，在xml界面未定义该属性值使用默认值
         */
        mFirstColor = array.getColor(R.styleable.CustomProgress_progressFirstColor, Color.RED);
        mSecondColor = array.getColor(R.styleable.CustomProgress_progressSecondColor, Color.BLUE);
        mOvalWidth = array.getDimensionPixelSize(R.styleable.CustomProgress_progressovalWidth, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
        mSpeed = array.getInt(R.styleable.CustomProgress_progressSpeed, 20);

        array.recycle();
        mPaint = new Paint();

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    mProgress++;
                    if (mProgress == 360) {
                        mProgress = 0;
                        //置换两个值
                        int temColor = mFirstColor;
                        mFirstColor = mSecondColor;
                        mSecondColor = temColor;
                    }
                    postInvalidate();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();

    }


    int mwidth;
    int mHeight;

    /**
     * 测量时为了在未获得精确值的情况下，为该值赋值默认值
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widmode = MeasureSpec.getMode(widthMeasureSpec);
        int widsize = MeasureSpec.getSize(widthMeasureSpec);

        if (widmode == MeasureSpec.EXACTLY) {
            mwidth = widsize;
        } else {//自定义view 如果为设置具体的宽度，则使用默认宽度
            mwidth = (int) getResources().getDimension(R.dimen.progress_width);
        }
        setMeasuredDimension(widsize,widsize);
    }


    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

        int centre = getWidth() / 2;
        int radius = centre - mOvalWidth / 2;

        mPaint = new Paint();
        mPaint.setStrokeWidth(mOvalWidth);
        mPaint.setAntiAlias(true);//抗锯齿;
        mPaint.setStyle(Paint.Style.STROKE);//设置空心

        mFirstColor = getResources().getColor(R.color.colorAccent);
        mPaint.setColor(mFirstColor);
        canvas.drawCircle(centre, centre, radius, mPaint); // 画出圆环

        mPaint.setColor(mSecondColor);
        RectF rectF = new RectF(centre - radius, centre - radius, centre + radius, centre + radius);
        canvas.drawArc(rectF, -90, mProgress, false, mPaint);
    }
}
