package com.alexkaz.task2.dagger.modules;

import com.alexkaz.task2.model.api.GitHubApi;
import com.alexkaz.task2.presenter.MainPresenter;
import com.alexkaz.task2.presenter.MainPresenterImpl;
import com.alexkaz.task2.util.ConnInfoHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Provides
    @Singleton
    MainPresenter provideMainPresenter(ConnInfoHelper connInfo, GitHubApi api){
        return new MainPresenterImpl(connInfo, api);
    }

}
