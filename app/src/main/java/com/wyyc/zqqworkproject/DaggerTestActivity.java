package com.wyyc.zqqworkproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class DaggerTestActivity extends AppCompatActivity {

    static {

        String describe = " @inject 主要有两个作用，一使用在构造方法，" +
                "Dagger通过inject标记可以在需要这个类的实例上找到这个构造函数并把相关实例new出来，" +
                "另一个就是标记在需要依赖的变量上为Dagger提供依赖";


        String note="测试过程中遇到的问题，通过@inject使用CoffeeMachine，由于当前类的构造方法通过注入，" +
                "而且构造方法需要的参数是CoffeeMaker，通过对CoffeeMaker的实现类SimpleMaker的构造方法" +
                "使用@inject是不行的，解决办法一通过修改参数直接为SimpleMaker,二是在SimpleComponent中添加" +
                "实现方法";
    }


//    @Inject
//    CoffeeMachine mCoffeeMachine;

//    @Inject
//    TitleFactory mFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger_test); 

//        DaggerSimpleComponent.builder().simpleModule(new SimpleModule()).build().inject(this);
//        DaggerSimpleComponent.builder().appComponent(App.getInstance().getAppComponent()).
//                simpleModule(new SimpleModule()).build().inject(this);


//        mCoffeeMachine.makeCoffee();

//        Cooker cooker = new Cooker("tom","拿铁");
//        SimpleMaker coffeeMaker = new SimpleMaker(cooker);
//        mCoffeeMachine = new CoffeeMachine(coffeeMaker);

//        Toast.makeText(this, mCoffeeMachine.makeCoffee(),Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, mFactory.getName(),Toast.LENGTH_SHORT).show();

    }


}
