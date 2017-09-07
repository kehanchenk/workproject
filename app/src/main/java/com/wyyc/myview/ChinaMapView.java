package com.wyyc.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.wyyc.bean.ProviceItem;
import com.wyyc.function.PathParser;
import com.wyyc.zqqworkproject.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by zqq on 2017/9/6.
 * Description:  此次添加可交互式中国地图
 */

public class ChinaMapView extends View {

    private Context mContext;

    private Paint mPaint;

    private int minWidth;

    private int minHeight;


    private RectF mRectF;
    private ProviceItem selectItem;  //被选择的 省份

    private List<ProviceItem> mProviceItems;

    private GestureDetectorCompat mGestureDetectorCompat;


    private onClickListener mListener;

    private int[] colorArray = new int[]{0xFF239BD7, 0xFF30A9E5, 0xFF80CBF1, 0xFFFFFFFF};
    private String[] city = new String[]{"安徽", "北京", "重庆", "福建", "广东", "甘肃", "广西壮族自治区", "贵州", "海南", "河北", "河南", "澳门特别行政区"
            , "黑龙江", "湖南", "湖北", "吉林", "江苏", "江西", "辽宁", "香港特别行政区", "内蒙古", "宁夏回族自治区", "青海", "陕西", "四川", "山东", "上海"
            , "山西", "天津", "台湾", "新疆维吾尔族自治区", "西藏自治区", "云南", "浙江"};
    private float scale = 1.3f;
    private int mViewwidth;
    private int mViewheight;
    private float mScale2;


    public ChinaMapView(Context context) {
        this(context, null);
    }

    public ChinaMapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {

        mContext = context;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        minWidth = context.getResources().getDimensionPixelSize(R.dimen.map_min_width);
        minHeight = context.getResources().getDimensionPixelSize(R.dimen.map_min_height);

        mThread.start();

        mGestureDetectorCompat = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {

                handleTouch(e.getX(), e.getY());
                return true;
            }
        });

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthmode = MeasureSpec.getMode(widthMeasureSpec);
        int widthsize = MeasureSpec.getSize(widthMeasureSpec);

        int heightmode = MeasureSpec.getMode(heightMeasureSpec);
        int heightsize = MeasureSpec.getSize(heightMeasureSpec);


        mViewwidth = widthsize;
        mViewheight = heightsize;

        switch (widthmode) {
            case MeasureSpec.EXACTLY:
                mViewwidth = widthsize > minWidth ? widthsize : minWidth;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                mViewwidth = minWidth;
                break;
        }

        //得到参考高度
        int computHeight = minHeight * mViewheight / minHeight;

        switch (heightmode) {
            //布局中写死了dp
            case MeasureSpec.EXACTLY:
                mViewheight = heightsize;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                //对照参考高度，取最大值
                mViewheight = minHeight > computHeight ? minHeight : computHeight;
                break;
        }


        setMeasuredDimension(MeasureSpec.makeMeasureSpec(mViewwidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(mViewheight, MeasureSpec.EXACTLY));


    }

    public void setListener(onClickListener listener) {
        mListener = listener;
    }

    /**
     * 点击事件处理
     *
     * @param x
     * @param y
     */
    private void handleTouch(float x, float y) {

        if (mProviceItems != null) {

            for (ProviceItem proviceItem : mProviceItems) {

                if (proviceItem.isTouch((x / scale), (y / scale))) {
                    selectItem = proviceItem;

                    mListener.onClick(proviceItem.getCity());
                    postInvalidate();
                    break;
                }
            }
        }
    }

    public interface onClickListener {

        void onClick(String s);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mProviceItems != null) {
            canvas.save();
            canvas.drawColor(Color.GRAY);
            canvas.scale(scale, scale);
            for (ProviceItem proviceItem : mProviceItems) {

                if (proviceItem != selectItem) {
                    proviceItem.onDrawItem(canvas, mPaint, false);
                }
                if (selectItem != null) {
                    selectItem.onDrawItem(canvas, mPaint, true);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetectorCompat.onTouchEvent(event);  //将事件交给mGestureDetectorCompat
    }

    /**
     * 线程去解析xml 获取每一个省份的 path
     */
    Thread mThread = new Thread() {
        @Override
        public void run() {
            List<ProviceItem> itemList = new ArrayList<>();
            try {
                InputStream stream = mContext.getResources().openRawResource(R.raw.chinamap);
                DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder(); //解析输入流 得到Docunmet实例
                Document document = builder.parse(stream);  //解析document

                NodeList items = document.getElementsByTagName("path");
                float left = -1;
                float right = -1;
                float top = -1;
                float bottom = -1;
                for (int i = 0; i < items.getLength(); i++) {

                    Element element = (Element) items.item(i);

                    String pathData = element.getAttribute("android:pathData");

                    Path path = PathParser.createPathFromPathData(pathData);

                    RectF rectF = new RectF();
                    path.computeBounds(rectF, true);

                    left = left == -1 ? rectF.left : Math.min(rectF.left, left);
                    top = top == -1 ? rectF.top : Math.min(rectF.top, top);

                    right = right == -1 ? rectF.right : Math.max(rectF.right, right);
                    bottom = bottom == -1 ? rectF.bottom : Math.max(rectF.bottom, bottom);

                    ProviceItem proviceItem = new ProviceItem(path);
                    itemList.add(proviceItem);

                }
                mRectF = new RectF(left, top, right, bottom);

            } catch (Exception e) {
                e.printStackTrace();
            }
            mProviceItems = itemList;
            mHandler.sendEmptyMessage(1);


        }
    };

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            scale = mViewwidth / mRectF.width();
            mScale2 = mViewheight / mRectF.height();

            scale = Math.min(scale, mScale2);

            if (mProviceItems == null) {
                return;
            }

            int color = Color.WHITE;

            for (int i = 0; i < mProviceItems.size(); i++) {
                int flag = i % 4;
                switch (flag) {
                    case 1:
                        color = colorArray[0];
                        break;
                    case 2:
                        color = colorArray[1];
                        break;
                    case 3:
                        color = colorArray[2];
                        break;
                    default:
                        color = Color.WHITE;
                        break;
                }

                mProviceItems.get(i).setColor(color);
                mProviceItems.get(i).setCity(city[i]);

            }
            postInvalidate();
        }
    };


}
