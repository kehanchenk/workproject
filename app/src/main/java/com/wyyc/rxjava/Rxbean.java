package com.wyyc.rxjava;

/**
 * Created by Administrator on 2017/1/11.
 */
public class Rxbean {

    private String name;
    private String age;
    private String sex;
    private String adress;


    public Rxbean(String name, String age, String sex, String adress) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.adress = adress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
