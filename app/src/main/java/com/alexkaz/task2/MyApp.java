package com.alexkaz.task2;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.alexkaz.task2.dagger.components.DaggerMainComponent;
import com.alexkaz.task2.dagger.components.MainComponent;
import com.alexkaz.task2.dagger.modules.AppModule;

public class MyApp extends Application {

    private  MainComponent component;
    private static MyApp app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
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

    public static boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) app.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm != null ? cm.getActiveNetworkInfo() : null;
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
