package com.wyyc.daggertest.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by Administrator on 2017/3/31.
 */


@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface Stringone {

}
