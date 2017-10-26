package com.alexkaz.task2.dagger.modules;

import com.alexkaz.task2.model.api.GitHubApi;

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

}
