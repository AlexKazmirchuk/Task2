package com.alexkaz.task2.dagger.modules;

import android.content.Context;

import com.alexkaz.task2.model.api.GitHubApi;
import com.alexkaz.task2.util.ConnInfoHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    GitHubApi provideGitHubApi(){
        return new Retrofit.Builder()
                .baseUrl(GitHubApi.END_POINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GitHubApi.class);
    }

    @Provides
    @Singleton
    ConnInfoHelper provideConnInfoHelper(Context context){
        return new ConnInfoHelper(context);
    }

}
