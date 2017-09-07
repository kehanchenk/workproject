package com.wyyc.rxjava;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by zqq on 2016/12/29.
 */
public class RxjavaTest {


    //观察者
    Observer<String> mObserver = new Observer<String>() {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(String s) {

        }
    };

    //Subscriber是Observer的实现抽象类类
//    Observer 即观察者，它决定事件触发的时候将有怎样的行为
    Subscriber<String> mSubscriber = new Subscriber<String>() {

        //此为Subscriber新增的的方法，在事件没有被发送之前调用，可以用于一些准备工作
        //附次为子线程的方法
        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(String s) {

        }
    };


    //Observable 即被观察者，它决定什么时候触发事件以及触发怎样的事件
    //可以看到的是传入一个OnSubscribe作为参数，它的作用相当于计划表，当Observable被订阅的时候，OnSubscribe.call()直接被调用


    Observable mObservable = Observable.create(new Observable.OnSubscribe<String>() {

        @Override
        public void call(Subscriber<? super String> subscriber) {
            subscriber.onNext("Hello");
            subscriber.onNext("Hi");
            subscriber.onNext("World");
            subscriber.onCompleted();
        }
    });

//
//    //create方法是Rxjava最基本创建事件序列的方法，除此之外还可以通过其他的方式
//    //just 将传入的对象 依次发送出来
//    Observable mObservable2 = Observable.just("Hello", "Hi", "World");
//    //from  将传入的数组拆分成具体的对象后，依次发送出来
    String[] words = {"Hello", "Hi", "World"};
    Observable mObservable3 = Observable.from(words);





    // 上述两种方法会将会依次调用：
// onNext("Hello");
// onNext("Hi");
// onNext("World");
// onCompleted();

    //订阅事件 Subscribe(订阅)

    static {

        String nd="在不指定线程的情况下，RxJava 遵循的是线程不变的原则，即：在哪个线程调用 subscribe () " +
                "就在哪个线程生产事件；在哪个线程生产事件，就在哪个线程消费事件。如果需要切换线程，就需要用到 Scheduler（调度器）";
    }









}














