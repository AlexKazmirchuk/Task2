package com.alexkaz.task2.dagger.modules;

import android.content.Context;

import com.alexkaz.task2.model.api.GitHubApi;
import com.alexkaz.task2.util.OfflineResponseCacheInterceptor;
import com.alexkaz.task2.util.ResponseCacheInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    GitHubApi provideGitHubApi(OkHttpClient client){
        return new Retrofit.Builder()
                .baseUrl(GitHubApi.END_POINT)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(GitHubApi.class);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttp(Context context){
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(context.getCacheDir(), cacheSize);
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(new ResponseCacheInterceptor())
                .addInterceptor(new OfflineResponseCacheInterceptor())
                .cache(cache)
                .build();
    }

}
