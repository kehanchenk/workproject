package com.wyyc.rxjava;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.wyyc.zqqworkproject.R;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxjavaTestActivity extends AppCompatActivity {


    static {
        String text = "Rxjava其实很简单，创建一个Observable和 Subscriber,通过subscribe()将他们串联起来，一次Rxjava就基本完成了";
    }

    private ImageView mImageView;
    private Context mContext;

    //  提升意识 好的时间安排  合适具体的计划
    // 补基础  数据结构 算法  UI  网络 线程
//  1.  view 的绘制 布局  触摸反馈
//  2.  http原理 tcp原理  ip原理
//  3.  线程 线程池 handler  asynctask

    //保持嗅觉  持续提升     书籍：http图解  图解HTTP     android群英传    Android开发艺术探索  ionic  阿里weex


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_test);
        mContext = this;
        mImageView = (ImageView) findViewById(R.id.iv_text);
        //被观察者创建一个观察者，通过参数传入subscriber(观察者)  它决定事件触发的时候将有怎样的行为
//        由指定的一个 drawable 文件 id drawableRes 取得图片，并显示在 ImageView 中，并在出现异常的时候打印 Toast 报错：
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = getResources().getDrawable(R.drawable.android_18);
                //被观察者调用观察者的回调方法  即观察者设计模式
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
//            使用 subscribeOn() 和 observeOn() 两个方法来对线程进行控制了。 * subscribeOn(): 指定 subscribe() 所发生的线程，
//            即 Observable.OnSubscribe 被激活时所处的线程。或者叫做事件产生的线程。 * observeOn(): 指定 Subscriber 所运行在的线程。或者叫做事件消费的线程。
//            Observable 和 Observer 通过 subscribe() 方法实现订阅关系，从而 Observable 可以在需要的时候发出事件来通知 Observer。
        })
//                加载图片将会发生在 IO 线程，而设置图片则被设定在了主线程。这就意味着，即使加载图片耗费了几十甚至几百毫秒的时间，也不会造成丝毫界面的卡顿。
                .subscribeOn(Schedulers.io())//指定subscribe()发送在IO线程中
                .observeOn(AndroidSchedulers.mainThread())//指定Subscriber的回调发生在主线程
                .subscribe(new Observer<Drawable>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(RxjavaTestActivity.this, "onCompleted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e("zaa", e.getMessage());
                        Toast.makeText(RxjavaTestActivity.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(Drawable drawable) {
                        mImageView.setImageDrawable(drawable);
                    }
                });


        //该栗子是将数组中的字符依次打印
        List<Rxbean> rxbeanList = new ArrayList<>();
        rxbeanList.add(new Rxbean("tom", "12", "man", "北京"));
        rxbeanList.add(new Rxbean("bob", "22", "woman", "上海"));
        rxbeanList.add(new Rxbean("lils", "12", "man", "北京"));
        rxbeanList.add(new Rxbean("res", "122", "man", "北京"));




        Observable.just("image/logi.png")
                .map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String s) {
                        //此处完成转化
                        return null;
                    }
                }).subscribe(new Action1<Bitmap>() {
            @Override
            public void call(Bitmap bitmap) {

            }
        });


        Observable.from(rxbeanList)
                .flatMap(new Func1<Rxbean, Observable<String>>() {
                    @Override
                    public Observable<String> call(Rxbean rxbean) {

                        return Observable.from(new String[]{rxbean.getAdress()});
                    }

                }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {

            }
        });


        Observable.from(rxbeanList)  //将传入的数组或 Iterable 拆分成具体对象后，依次发送出来。
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Rxbean>() {
                    @Override
                    public void call(Rxbean rxbean) {
                        Log.e("zaa", rxbean.getAge());
                    }
                });

        Observable.from(rxbeanList)
                .map(new Func1<Rxbean, String>() {
                    @Override
                    public String call(Rxbean rxbean) {
                        return rxbean.getAdress();
                    }
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(String o) {
                        Log.e("zaa", o);
                    }
                });


        String[] name = {"tom", "button", "syccsess"};
//        Observable.from(name)
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        Log.e("zaa", s);
//                    }
//                });


        //线程控制  Scheduler
        //Rxjava 在默认的情况下，是遵循线程不变的原则的，即在哪个线程调用subscribe().就是在哪个线程去生产事件的，
        // 在哪个线程生产事件就是在哪个线程消费时间的，，如果需要切换线程就要用到Scheduler


//        Schedulers.io();//指定Io操作在该线程中
//        Schedulers.computation();//指定在计算线程中使用
//        Schedulers.immediate();//指定在当前线程中运行
//        Schedulers.newThread();//总是启用新线程执行
//        AndroidSchedulers.mainThread();//指定操作在主线程中进行

        //栗子   一次打印字符串
        Observable.just("tom", "bos", "sjia", "zqq")
                .subscribeOn(Schedulers.io())  //指定subscribe()发生在IO线程中
                .observeOn(AndroidSchedulers.mainThread()) //指定Subscriber的回调发生在主线程
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e("zaa", s);
                    }
                });



//        换句话说，observeOn() 指定的是它之后的操作所在的线程
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }



}
