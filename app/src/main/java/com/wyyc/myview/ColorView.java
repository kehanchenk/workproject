package com.wyyc.myview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.wyyc.zqqworkproject.R;

/**
 * Created by zqq on 2017/8/28.
 * Description: 自定义view 颜色矩阵以及颜色过滤问题
 *   点击图片改变图片的颜色处理selector 的方式，还可以通过设置色彩过滤的方式实现，，
 */

public class ColorView extends View {

    private Paint mPaint;
    private Bitmap mBitmap;

    private boolean isClick;// 用来标识控件是否被点击过

    public ColorView(Context context) {
        super(context);
    }

    public ColorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initPaint();

        initRes();

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                //判断是否被点击
                if (isClick) {
                    // 如果已经被点击了则点击时设置颜色过滤为空还原本色
                    mPaint.setColorFilter(null);
                    isClick = false;
                } else {
                    mPaint.setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0X00FFFF00));
//                    mPaint.setColorFilter(new PorterDuffColorFilter(Color.BLUE, PorterDuff.Mode.DARKEN));
                    isClick = true;
                }

                invalidate();

            }
        });
    }

    private void initRes() {

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_activity);

    }

    private void initPaint() {

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制位图
        canvas.drawBitmap(mBitmap, 100, 100, mPaint);
    }
}
