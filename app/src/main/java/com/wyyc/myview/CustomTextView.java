package com.wyyc.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import com.wyyc.zqqworkproject.R;

/**
 * Created by zqq on 2017/2/27.
 */
public class CustomTextView extends TextView {


    /**
     * 文本内容
     */
    private String mTitleText;

    /**
     * 文本颜色
     */
    private int mTitleColor;

    /**
     * 字体大小
     */
    private int mTitleSize;

    /**
     * 绘制文本的控制范围
     */
    private Paint mPaint;
    private Rect mBond;


    public CustomTextView(Context context) { //默认各个构造方法都会执行三个参数的构造方法

        this(context, null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    /**
     * 获得自定义的样式属性
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /**
         * 通过以下方法获取自定义view属性
         */
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustemTextView, defStyleAttr, 0);
        int count = array.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.CustemTextView_titleText:
                    mTitleText = array.getString(attr);
                    break;
                case R.styleable.CustemTextView_titleColor:
                    mTitleColor = array.getColor(attr, Color.BLACK); //默认颜色黑色
                    break;
                case R.styleable.CustemTextView_titleSize: //默认设置为16sp Typevalue
                    mTitleSize = array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
            }
        }
        array.recycle();

        /**
         * 获得绘制文本的宽和高
         */
        mPaint = new Paint();
//        mPaint.setAntiAlias(true);//设置抗锯齿
        mBond = new Rect();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        int widthmode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heighmode = MeasureSpec.getMode(heightMeasureSpec);
        int heighSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        //判断是否是精确位置
        if (widthmode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            mPaint.setTextSize(mTitleSize);
            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBond);
            float textWidth = mBond.width();
            int v = (int) (getPaddingLeft() + textWidth + getPaddingRight());
            width = v;
        }

        if (heighmode == MeasureSpec.EXACTLY) {
            height = heighSize;
        } else {
            mPaint.setTextSize(mTitleSize);
            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBond);
            float textheight = mBond.height();
            int i = (int) (getPaddingTop() + textheight + getPaddingBottom());
            height = i;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG)); //设置抗锯齿
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
        mPaint.setColor(mTitleColor);
        canvas.drawText(mTitleText, getWidth() / 2 - mBond.width() / 2, getHeight() / 2 + mBond.height() / 2, mPaint);
    }


}
