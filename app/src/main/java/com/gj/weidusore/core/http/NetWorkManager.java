package com.gj.weidusore.core.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetWorkManager {
    private static NetWorkManager mInstance;
    private Retrofit retrofit;

    private NetWorkManager(){
        init();
    }

    public static NetWorkManager mInstance(){//单例模式
        if(mInstance==null){
            mInstance = new NetWorkManager();
        }
        return mInstance;
    }

    private void init() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//打印请求参数，请求结果

        OkHttpClient okHttpClient = new OkHttpClient.Builder()//自定义OkHttp
                .addInterceptor(interceptor)//设置拦截器
                .connectTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://172.17.8.100/small/")//base_url:http+域名
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//使用Rxjava对回调数据进行处理
                .addConverterFactory(GsonConverterFactory.create())//响应结果的解析器，包含gson，xml，protobuf
                .build();

    }

    public <T> T create(final Class<T> service) {
        return retrofit.create(service);
    }
}
