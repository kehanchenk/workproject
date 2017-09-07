package com.wyyc.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/3/22.
 */
public class GankBeanResult {

    public boolean error;

    //序列化结果
    public  @SerializedName("results") List<GankBean> mGankBeen;
}
