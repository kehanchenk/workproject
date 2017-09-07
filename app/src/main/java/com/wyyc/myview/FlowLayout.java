package com.wyyc.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zqq on 2017/3/9.
 * <p>
 * 流式布局
 */
public class FlowLayout extends ViewGroup {


    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * 直接使用系统的MarginLayoutParams
     *
     * @param attrs
     * @return
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        /**
         * 获得父容器为子view设置的测量模式以及大小
         */
        int widMode = MeasureSpec.getMode(widthMeasureSpec);
        int widSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        Log.e("zaa", "width" + widSize + "height" + heightSize);

        //如果是warp_content情况下

        /**
         * 每一行的宽度
         */
        int flowwidth = 0;
        /**
         * 每一行的高度
         */
        int flowHeight = 0;

        /**
         * 当前宽高
         */
        int width = 0;
        int height = 0;

        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View childAt = getChildAt(i);
            //测量每一个子view的宽高
            measureChild(childAt, widthMeasureSpec, heightMeasureSpec);
            //获得子view的布局参数
            MarginLayoutParams params = (MarginLayoutParams) childAt.getLayoutParams();
            //获得测量出的子view的宽
            int childwidth = childAt.getMeasuredWidth() + params.leftMargin + params.rightMargin;
            //获得测量的子view的高
            int childheight = childAt.getMeasuredHeight() + params.topMargin + params.bottomMargin;

            //对是否超过最大宽度的比较
            if (flowwidth + childwidth < widSize) {
                flowwidth += childwidth;
                //获取相对最大高度
                flowHeight = Math.max(flowHeight, childheight);
            } else {
//                Math.max(flowwidth, childwidth);
                width = flowwidth;//将最后一个的宽度作为测量宽度
                flowwidth = childwidth;//重新获取新的一行
                //叠加高度
                height += flowHeight;
                //开启记录下一行高度
                flowHeight = childheight;
            }
            //如果没有满一行，存在的情况，或者最后一行的问题
            if (i == count - 1) {//最后一行
                width = Math.max(flowwidth, width);
                height += flowHeight;
            }
            //判断测量模式
            setMeasuredDimension((widMode == MeasureSpec.EXACTLY) ? widSize : width,
                    (heightMode == MeasureSpec.EXACTLY) ? heightSize : height);
        }

    }

    /**
     * 存储所有的View，按行记录
     */
    private List<List<View>> mAllViews = new ArrayList<List<View>>();
    /**
     * 记录每一行的最大高度
     */
    private List<Integer> mLineHeight = new ArrayList<Integer>();

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        //layout 存在多次调用的情况，所以使用前需要clear
        mAllViews.clear();
        mLineHeight.clear();

        int count = getChildCount();
        int lineWidth = 0;
        int lineHeight = 0;

        // 存储每一行所有的childView
        List<View> lineViews = new ArrayList<View>();

        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);

            MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
            //获得测量宽高
            int childwidth = view.getMeasuredWidth();
            int childheight = view.getMeasuredHeight();

            //需要换行
            if (childwidth + params.leftMargin + params.rightMargin + lineWidth > getWidth()) {

                //记录一行的所有view以及最大高度
                mLineHeight.add(lineHeight);
                //将当前view保存，然后开启新的ArrayList保存下一行的childview
                mAllViews.add(lineViews);
                lineWidth = 0;//重置宽高
                lineViews = new ArrayList<>();
            }

            /**
             * 如果不需要换行
             */
            lineWidth += childwidth + params.leftMargin + params.rightMargin;
            lineHeight = Math.max(lineHeight, childheight + params.topMargin + params.bottomMargin);
            lineViews.add(view);
        }

        //记录最后一行
        mLineHeight.add(lineHeight);
        mAllViews.add(lineViews);

        //得到总行数
        int lineNums = mAllViews.size();

        int left = 0;
        int top = 0;
        for (int i = 0; i < lineNums; i++) {

            //每一行所有的view
            List<View> views = mAllViews.get(i);
            //当前行的最大高度
            lineHeight = mLineHeight.get(i);

            //遍历每一行，，获得每一个view
            for (int j = 0; j < views.size(); j++) {

                View view = views.get(j);
                if (view.getVisibility() == View.GONE) {
                    continue;
                }
                MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
                //计算view的left top right, bottom
                int lc = left + params.leftMargin;
                int tc = top + params.topMargin;
                int rc = lc + view.getMeasuredWidth();
                int bc = tc + view.getMeasuredHeight();

                view.layout(lc, tc, rc, bc);

                left += view.getMeasuredWidth() + params.leftMargin + params.rightMargin;

            }
            left = 0;
            top += lineHeight;

        }


    }


}
