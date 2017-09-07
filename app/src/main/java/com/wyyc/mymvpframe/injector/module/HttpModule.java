package com.wyyc.mymvpframe.injector.module;

import android.support.annotation.NonNull;
import android.util.Log;

import com.wyyc.network.api.ZqqApi;
import com.wyyc.zqqworkproject.BuildConfig;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zqq on 2017/6/20.
 *  HTTP模块
 */

@Module
public class HttpModule {


    @Provides
    @Singleton
    public OkHttpClient providerOkHttpClient() {

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();

        //开发模式拦截 URL log 信息
        if (BuildConfig.DEBUG) {
            //添加日志拦截
//            builder.addInterceptor(sLoggingInterceptor);
        }

        return builder.connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();

    }

    @Provides
    @Singleton
    public Retrofit providerRetrofit(OkHttpClient okHttpClient) {

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ZqqApi.BASE_URL)
                .build();

        return retrofit;
    }

    @Provides
    @Singleton
    public ZqqApi providerZqqApi(Retrofit retrofit) {

        return retrofit.create(ZqqApi.class);
    }


    @Provides
    @Singleton
    public Interceptor providerInterceptor() {

        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Buffer requestBuffer = new Buffer();
                if (request.body() != null) {
                    request.body().writeTo(requestBuffer);
                } else {
                    Log.e("zhu", "request.body() == null");
                }

                //打印URL信息
                Log.w("zhu", request.url() + (request.body() != null ? "?" + _parseParams(request.body(), requestBuffer) : ""));
                return null;
            }
        };
    }

    @NonNull
    private static String _parseParams(RequestBody body, Buffer requestBuffer) throws UnsupportedEncodingException {
        if (body.contentType() != null && !body.contentType().toString().contains("multipart")) {
            return URLDecoder.decode(requestBuffer.readUtf8(), "UTF-8");
        }
        return "null";
    }


}
