package com.wyyc.fragement.CacheFragment;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import com.wyyc.App;
import com.wyyc.bean.Item;
import com.wyyc.function.GankBeanResultToItemMapper;
import com.wyyc.network.Network;
import com.wyyc.zqqworkproject.R;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

/**
 * Created by zaa on 2017/3/24.
 */
public class Data {

    private static Data data;

    private int dataSource;
    BehaviorSubject<List<Item>> cache;
    private static final int DATA_SOURCE_MEMORY = 1;
    private static final int DATA_SOURCE_DISK = 2;
    private static final int DATA_SOURCE_NETWORK = 3;

    @IntDef({DATA_SOURCE_MEMORY, DATA_SOURCE_DISK, DATA_SOURCE_NETWORK}) @interface DataSource {}

    static {

        String describe = " Rxjava中有一个使用较少的类叫做Subject,他是一种既是 Observable ,又是 Observer 的特殊东西，" +
                "因此可以作为中间件来作为数据传递，例如 可以用作他的子类 BehavoirSubject 来制作缓存  代码大致如下";
//        api.getData()
//                .subscribe(behaviorSubject); //网络数据就会被缓存
//
//        behaviorSubject.subscribe(observer);//之前的缓存会被直接送达observer


    }


    private Data() {
    }

    public  static  Data getInstance() {

        if (data == null) {
            data=new Data();
        }
        return data;
    }

    private void setDataSource(@DataSource int dataSource) {
        this.dataSource = dataSource;
    }

    public String getDataSourceText() {
        int dataSourceTextRes;
        switch (dataSource) {
            case DATA_SOURCE_MEMORY:
                dataSourceTextRes = R.string.data_source_memory;
                break;
            case DATA_SOURCE_DISK:
                dataSourceTextRes = R.string.data_source_disk;
                break;
            case DATA_SOURCE_NETWORK:
                dataSourceTextRes = R.string.data_source_network;
                break;
            default:
                dataSourceTextRes = R.string.data_source_network;
        }
        return App.getInstance().getString(dataSourceTextRes);
    }



    public void LoadFromNetWork() {

        Network.getGankApi().getGankImage(20, 1)
                .subscribeOn(Schedulers.io())
                .map(GankBeanResultToItemMapper.getInstance())
                .doOnNext(new Action1<List<Item>>() {
                    @Override
                    public void call(List<Item> items) {
                        //将读取的数据写入存储
                        Database.getInstance().writeItems(items);
                    }
                }).subscribe(new Action1<List<Item>>() {
            @Override
            public void call(List<Item> items) {
                cache.onNext(items);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    public Subscription subscribeData(@NonNull Observer<List<Item>> observer) {

        if (cache == null) {
            cache = BehaviorSubject.create();
            Observable.create(new Observable.OnSubscribe<List<Item>>() {
                @Override
                public void call(Subscriber<? super List<Item>> subscriber) {

                    List<Item> items = Database.getInstance().readItems();
                    if (items == null) {
                        setDataSource(DATA_SOURCE_NETWORK);
                        LoadFromNetWork();
                    } else {
                        setDataSource(DATA_SOURCE_DISK);
                        cache.onNext(items);
                    }
                }
            }).subscribeOn(Schedulers.io())
                    .subscribe(cache);
        } else {
            setDataSource(DATA_SOURCE_MEMORY);
        }

        return cache.observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    public void clearMemoryCache() {
        cache = null;
    }

    public void clearMemoryAndDiskCache() {
        clearMemoryCache();
        Database.getInstance().delete();
    }

}
