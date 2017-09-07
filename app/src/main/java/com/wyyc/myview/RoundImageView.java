package com.wyyc.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.wyyc.zqqworkproject.R;

/**
 * Created by zqq on 2017/8/29.
 * Description:   圆形图像
 */

public class RoundImageView extends ImageView {

    /**
     * 图片的类型，圆形or圆角
     */
    private int type;
    private static final int TYPE_CIRCLE = 0;
    private static final int TYPE_ROUND = 1;

    /**
     * 圆角大小的默认值
     */
    private static final int BODER_RADIUS_DEFAULT = 10;
    /**
     * 圆角的大小
     */
    private int mBorderRadius;

    /**
     * 圆角的半径
     */
    private int mRadius;

    /**
     * view的宽度
     */
    private int mWidth;

    /**
     * 3x3 矩阵，主要用于缩小放大
     */
    private Matrix mMatrix;


    private Bitmap mBitmap;
    private Paint mPaint;
    private BitmapShader mShader;
    private RectF mRectF;

    public RoundImageView(Context context) {
        super(context);
        initRes();
    }


    public RoundImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);

        mBorderRadius = a.getDimensionPixelSize(R.styleable.RoundImageView_roundRadius,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        BODER_RADIUS_DEFAULT, getResources().getDisplayMetrics()));  //默认10dp

        type = a.getInt(R.styleable.RoundImageView_type, TYPE_CIRCLE);

        a.recycle();


        initRes();
    }


    private void initRes() {

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.ANTI_ALIAS_FLAG);
        mMatrix = new Matrix();



    }

    /**
     * 初始化BitmapShader
     */
    private void setUpShader() {

        Drawable drawable = getDrawable();

        if (drawable == null) {
            return;
        }

        mBitmap = drawableToBitamp(drawable);
//        setImageDrawable(null);
//        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_round);
        mShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        float scale = 1.0f;

        if (type == TYPE_CIRCLE) {
            // 拿到bitmap宽或高的小值
            int bSize = Math.min(mBitmap.getWidth(), mBitmap.getHeight());
            scale = mWidth * 1.0f / bSize;
        } else if (type == TYPE_ROUND) {
            // 如果图片的宽或者高与view的宽高不匹配，计算出需要缩放的比例；缩放后的图片的宽高，一定要大于我们view的宽高；所以我们这里取大值；
            scale = Math.max(getWidth() * 1.0f / mBitmap.getWidth(), getHeight()
                    * 1.0f / mBitmap.getHeight());
        }

        // shader的变换矩阵，我们这里主要用于放大或者缩小
        mMatrix.setScale(scale, scale);
        // 设置变换矩阵
        mShader.setLocalMatrix(mMatrix);
        // 设置shader
        mPaint.setShader(mShader);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        /**
         * 如果类型是圆形，则强制改变view的宽高一致，以小值为准
         */
        if (type == TYPE_CIRCLE) {
            mWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
            mRadius = mWidth / 2;
            setMeasuredDimension(mWidth, mWidth);
        }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (getDrawable() == null)
        {
            return;
        }

        setUpShader();

        if (type == TYPE_CIRCLE) {


            canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
        } else {
            canvas.drawRoundRect(mRectF, mBorderRadius, mBorderRadius, mPaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 圆角图片的范围
        if (type == TYPE_ROUND) {
            mRectF = new RectF(0, 0, getWidth(), getHeight());
        }
    }

    private static final String STATE_INSTANCE = "state_instance";
    private static final String STATE_TYPE = "state_type";
    private static final String STATE_BORDER_RADIUS = "state_border_radius";

    @Override
    protected Parcelable onSaveInstanceState()
    {
        Bundle bundle = new Bundle();
        bundle.putParcelable(STATE_INSTANCE, super.onSaveInstanceState());
        bundle.putInt(STATE_TYPE, type);
        bundle.putInt(STATE_BORDER_RADIUS, mBorderRadius);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state)
    {
        if (state instanceof Bundle)
        {
            Bundle bundle = (Bundle) state;
            super.onRestoreInstanceState(((Bundle) state)
                    .getParcelable(STATE_INSTANCE));
            this.type = bundle.getInt(STATE_TYPE);
            this.mBorderRadius = bundle.getInt(STATE_BORDER_RADIUS);
        } else
        {
            super.onRestoreInstanceState(state);
        }

    }

    /**
     * drawable转bitmap
     *
     * @param drawable
     * @return
     */
    private Bitmap drawableToBitamp(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

}
