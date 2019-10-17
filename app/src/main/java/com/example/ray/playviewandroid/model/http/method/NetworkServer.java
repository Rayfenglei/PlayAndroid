package com.example.ray.playviewandroid.model.http.method;

import android.content.Context;

import com.example.ray.playviewandroid.PlayApplication;
import com.example.ray.playviewandroid.model.http.api.NetworkApi;
import com.example.ray.playviewandroid.model.http.interceptor.AddCookiesInterceptor;
import com.example.ray.playviewandroid.model.http.interceptor.CacheInterceptor;
import com.example.ray.playviewandroid.model.http.interceptor.SaveCookiesInterceptor;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkServer {
    private String base_url = "https://www.wanandroid.com/";
    private volatile static NetworkServer mInstance;
    private NetworkApi mService;
    private Context mContext;
    private NetworkServer(){
        mContext = PlayApplication.getContext();
    }
    public static NetworkServer getInstance(){
        if (mInstance == null){
            synchronized (NetworkServer.class){
                if (mInstance == null)
                {
                    mInstance = new NetworkServer();
                }
            }
        }
        return mInstance;
    }
    /*
    * synchronized 保持同步 切换太快数据不加载
    * */
    public synchronized NetworkApi getNetworkDatas(){
        if (mService == null){
            Cache cache = new Cache(new File(mContext.getCacheDir(), "HttpCache"), 1024*1024*10);
            //1.okhttpClient对象
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder().cache(cache)
                                            .retryOnConnectionFailure(true)
                                            .connectTimeout(10, TimeUnit.SECONDS)
                                            .readTimeout(20,TimeUnit.SECONDS)
                                            .writeTimeout(20,TimeUnit.SECONDS)
                                            .addInterceptor(new AddCookiesInterceptor(mContext))
                                            .addInterceptor(new SaveCookiesInterceptor(mContext))
                                            .addInterceptor(new CacheInterceptor())
                                            .build();
            //2.通过RequestBody.create 创建requestBody对象
            String value = "{username:admin;password:admin}";
            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");//"类型,字节码"
            RequestBody requestBody =RequestBody.create(mediaType, value);
            //3构造Request,
            Request request = new Request.Builder().get().url("www.baidu.com").post(requestBody).build();
            //4将Request封装成call
            Call call = okHttpClient.newCall(request);
            //5，执行call，这个方法是异步请求数据
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                //由于OkHttp在解析response的时候依靠的是response头信息当中的Content-Type字段来判断解码方式
                //OkHttp会使用默认的UTF-8编码方式来解码
                //这里使用的是异步加载，如果需要使用控件，则在主线程中调用
                @Override
                public void onResponse(Call call, Response response) throws IOException {

                }
            });

            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(base_url)
                                    .client(okHttpClient)
                                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
            mService = retrofit.create(NetworkApi.class);
        }
        return mService;
    }

}
