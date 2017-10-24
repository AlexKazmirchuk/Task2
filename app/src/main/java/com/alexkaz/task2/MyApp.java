package com.alexkaz.task2;

import android.app.Application;

import com.alexkaz.task2.dagger.components.DaggerMainComponent;
import com.alexkaz.task2.dagger.components.MainComponent;
import com.alexkaz.task2.dagger.modules.AppModule;

public class MyApp extends Application {

    private  MainComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init(){
        component = DaggerMainComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public MainComponent getMainComponent(){
        if (component != null){
            return component;
        } else {
            init();
            return component;
        }
    }
}
