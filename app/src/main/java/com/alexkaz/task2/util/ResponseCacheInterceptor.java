package com.alexkaz.task2.util;

import java.io.IOException;
import okhttp3.Interceptor;

public class ResponseCacheInterceptor implements Interceptor {

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        okhttp3.Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
                .header("Cache-Control", "public, max-age=" + 60)
                .build();
    }
}
