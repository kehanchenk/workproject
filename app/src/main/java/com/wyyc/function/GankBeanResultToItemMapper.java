package com.wyyc.function;

import com.wyyc.bean.GankBean;
import com.wyyc.bean.GankBeanResult;
import com.wyyc.bean.Item;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import rx.functions.Func1;

/**
 * Created by zqq on 2017/3/22.
 * <p/>
 * 由于接口返回结果需要处理数据，则实现该接口处理完之后再返回
 */
public class GankBeanResultToItemMapper implements Func1<GankBeanResult, List<Item>> {


    private  static GankBeanResultToItemMapper mItemMapper = new GankBeanResultToItemMapper();

    private GankBeanResultToItemMapper() {

    }

    public  static GankBeanResultToItemMapper getInstance() {

        return mItemMapper;
    }


    @Override
    public List<Item> call(GankBeanResult result) {

        List<GankBean> gankBeen = result.mGankBeen;
        List<Item> items = new ArrayList<>(gankBeen.size());
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yy/MM/dd HH:mm:ss");

        for (GankBean bean : gankBeen) {
            Item item = new Item();
            try {
                Date date = inputFormat.parse(bean.createdAt);
                item.description=outputFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
                item.description = "unknown date";
            }
            item.imageUrl = bean.url;
            items.add(item);
        }
        return items;
    }
}
