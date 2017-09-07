package com.wyyc.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.wyyc.zqqworkproject.R;

/**
 * Created by Administrator on 2017/2/27.
 */
public class CustomImageView extends View {


    private String mTitleText;
    private int mTitleColor;
    private int mTitleSize;
    private Bitmap mBitmap;
    private int mImageScaleType;

    private Paint mPaint;
    private Rect mBond;
    private Rect mRect;
    private int mWidth;
    private int mHeight;
    // 缩放形式
    private int IMAGE_SCALE_FITXY = 0;
    private int IMAGE_SCALE_CENTER = 1;

    public CustomImageView(Context context) {
        this(context, null);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomImageView, defStyleAttr, 0);

        for (int i = 0; i < array.getIndexCount(); i++) {

            int index = array.getIndex(i);
            switch (index) {
                case R.styleable.CustomImageView_imageText:
                    mTitleText = array.getString(index);
                    break;
                case R.styleable.CustomImageView_imageTextColor:
                    mTitleColor = array.getColor(index, Color.BLACK);
                    break;
                case R.styleable.CustomImageView_imageTextSize:
                    mTitleSize = array.getDimensionPixelSize(index, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomImageView_image:
                    mBitmap = BitmapFactory.decodeResource(getResources(), array.getResourceId(index, 0));
                    break;
                case R.styleable.CustomImageView_imageScaleType:
                    mImageScaleType = array.getInt(index, 0);
                    break;
            }
        }

        array.recycle();
        mPaint = new Paint();
        mBond = new Rect();
        mRect = new Rect();
        mPaint.setTextSize(mTitleSize);
        // 计算了描绘字体需要的范围
        mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBond);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);


        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        } else {
            //根据文字 图片的宽度决定
            int imageWidth = getPaddingLeft() + mBitmap.getWidth() + getPaddingRight();
            int textWidth = getPaddingLeft() + mBond.width() + getPaddingRight();
            int max = Math.max(textWidth, imageWidth);

            mWidth = Math.min(max, widthSize);
            mWidth = widthSize;
        }

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else { //如果测量模式精确，则将文字高度，图片高度 和padding值相加
            int desire = getPaddingTop() + getPaddingBottom() + mBitmap.getHeight() + mBond.height();
            mHeight = Math.min(desire, heightSize);
        }
        setMeasuredDimension(mWidth, mHeight);
    }


    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

        //设置边框
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.CYAN);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        mRect.left = getPaddingLeft();
        mRect.right = mWidth - getPaddingRight();
        mRect.top = getPaddingTop();
        mRect.bottom = mHeight - getPaddingBottom();


        mPaint.setColor(mTitleColor);
        mPaint.setStyle(Paint.Style.FILL);

        /**
         * 由于绘制的文字是在onDraw方法中，所以存在的可能是绘制文字长度会超过测量的结果
         * 当前设置的宽度小于字体所需要的宽度  将字体设置为xxx...
         */
        if (mBond.width() > mWidth) {//此处存在字体设置过长，字体长度超过具体的view的宽度。

            TextPaint paint = new TextPaint(mPaint);
            String msg = TextUtils.ellipsize(mTitleText, paint, (float) mWidth - getPaddingLeft() -
                    getPaddingRight(), TextUtils.TruncateAt.END).toString();//截取固定的字符串长度，剩下的用。。。显示

            canvas.drawText(msg, getPaddingLeft(), mHeight - getPaddingBottom(), mPaint);
        } else {
            //正常情况下，将字体居中显示
            canvas.drawText(mTitleText, mWidth / 2 - mBond.width() * 1.0f / 2, mHeight - getPaddingBottom(), mPaint);
        }
        //取消使用掉的快
        mRect.bottom -= mBond.height();

        if (mImageScaleType == IMAGE_SCALE_FITXY) {

            canvas.drawBitmap(mBitmap, null, mRect, mPaint);
        } else {
            //居中显示
            mRect.left = mWidth / 2 - mBitmap.getWidth() / 2;
            mRect.right = mWidth / 2 + mBitmap.getWidth() / 2;
            mRect.top = (mHeight - mBond.height()) / 2 - mBitmap.getHeight() / 2;
            mRect.bottom = (mHeight - mBond.height()) / 2 + mBitmap.getHeight() / 2;
            canvas.drawBitmap(mBitmap, null, mRect, mPaint);
        }

    }
}
