package com.wyyc.bean;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;

/**
 * Created by zqq on 2017/9/6.
 * Description: 省份item
 */

public class ProviceItem {

    private Path mPath;

    private int mColor;

    private String mCity;

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public ProviceItem(Path path) {
        mPath = path;
    }


    /**
     * 绘制每一个省
     *
     * @param canvas
     * @param paint
     * @param isSelect 当前是否被选择
     */
    public void onDrawItem(Canvas canvas, Paint paint, Boolean isSelect) {

        if (isSelect) {

            //绘制背景
            paint.setStrokeWidth(2);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.FILL);
            paint.setShadowLayer(8, 0, 0, 0xffffff);

            canvas.drawPath(mPath, paint);

            //绘制本身
            paint.clearShadowLayer();
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(2);
            paint.setColor(mColor);
            canvas.drawPath(mPath, paint);

        } else {
            //绘制本身
            paint.clearShadowLayer();
            paint.setStrokeWidth(1);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(getColor());
            canvas.drawPath(mPath, paint);

            //绘制描边效果
            paint.setColor(0xFFD0E8F4);
            paint.setStrokeWidth(1);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(mPath, paint);
        }
    }

    /**
     * 判断当前item是否被点击
     */

    public Boolean isTouch(float x, float y) {

        //构造一个区域对象
        RectF rectF = new RectF();
        //计算控制点边界
        mPath.computeBounds(rectF, true);
        Region region = new Region();
        //设置区域路径和剪辑描述的区域
        region.setPath(mPath, new Region((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));


        return region.contains((int) x, (int) y);
    }


    public Path getPath() {
        return mPath;
    }

    public void setPath(Path path) {
        mPath = path;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }
}
