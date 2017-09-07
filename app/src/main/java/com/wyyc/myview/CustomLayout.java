package com.wyyc.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zqq on 2017/3/2.
 */
public class CustomLayout extends ViewGroup {


    public CustomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * 需要ViewGroup能够支持margin
     *
     * @param attrs
     * @return
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    /**
     * 计算所有ChildView的测量值以及模式
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        /**
         * 获得上级容器为其推荐的宽高以及模式
         */
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //计算出所有childview的宽高
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int measuredWidth;//测量的view的宽
        int measuredHeight;//测量的view的高
        int tWidth = 0;
        int dWidth = 0;
        int dHeighth = 0;
        int tHeighth = 0;
        MarginLayoutParams layoutParams;

        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
//            measureChild(view, widthMeasureSpec, heightMeasureSpec);
            measuredWidth = view.getMeasuredWidth();
            measuredHeight = view.getMeasuredHeight();
            layoutParams = (MarginLayoutParams) view.getLayoutParams();
            //之前的两个子view 获取view中最大的值
            tWidth = measuredWidth + layoutParams.leftMargin + layoutParams.rightMargin;
            if (tWidth > dWidth) {
                dWidth = tWidth;
            }
            dHeighth = measuredHeight + layoutParams.leftMargin + layoutParams.rightMargin;
            if (dHeighth > tHeighth) {
                tHeighth = dHeighth;
            }
        }

        /**
         * 如果是warp_content,则设置为计算的值，否则直接使用父容器计算的值
         */
        setMeasuredDimension((widMode == MeasureSpec.EXACTLY) ? widthSize : dWidth,
                (heightMode == MeasureSpec.EXACTLY) ? heightSize : tHeighth);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {


        int meaHeight = 0;
        int meaWidth = 0;
        MarginLayoutParams params;

        /**
         * 遍历所有子view
         */
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            meaWidth = view.getMeasuredHeight();
            meaHeight = view.getMeasuredWidth();
            params = (MarginLayoutParams) view.getLayoutParams();

            int cl = 0, ct = 0, cr = 0, cb = 0;

            switch (i) {
                case 0:
                    cl = params.leftMargin;
                    ct = params.topMargin;
                    break;
                case 1:
                    cl = getWidth() - meaWidth - params.leftMargin - params.rightMargin;
                    ct = params.topMargin;
                    break;
                case 2:
                    cl = params.leftMargin;
                    ct = getHeight() - meaHeight - params.topMargin - params.bottomMargin;
                    break;
                case 3:
                    cl = getWidth() - meaWidth - params.leftMargin - params.rightMargin;
                    ct = getHeight() - meaHeight - params.bottomMargin;
            }
            cr = meaWidth + cl;
            cb = meaHeight + ct;
            view.layout(cl, ct, cr, cb);
        }


    }
}
