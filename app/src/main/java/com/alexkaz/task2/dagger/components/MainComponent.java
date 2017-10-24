package com.alexkaz.task2.dagger.components;

import com.alexkaz.task2.MainActivity;
import com.alexkaz.task2.dagger.modules.AppModule;
import com.alexkaz.task2.dagger.modules.NetworkModule;
import com.alexkaz.task2.dagger.modules.PresenterModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class,
                      AppModule.class,
                      PresenterModule.class})
public interface MainComponent {
    void inject(MainActivity activity);
}
