package com.alexkaz.task2.dagger.modules;

import android.content.Context;

import com.alexkaz.task2.MyApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private MyApp app;

    public AppModule(MyApp app) {
        this.app = app;
    }

    @Provides
    @Singleton
    MyApp provideMyApp(){
        return app;
    }

    @Provides
    @Singleton
    Context provideAppContext(){
        return app;
    }
}
