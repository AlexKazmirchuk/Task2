package com.alexkaz.task2.util;

import com.alexkaz.task2.MyApp;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;

public class OfflineResponseCacheInterceptor implements Interceptor {
    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!MyApp.isOnline()) {
            request = request.newBuilder()
                    .header("Cache-Control",
                            "public, only-if-cached, max-stale=" + 2419200)
                    .build();
        }
        return chain.proceed(request);
    }
}
