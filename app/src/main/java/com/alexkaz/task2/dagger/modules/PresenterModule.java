package com.alexkaz.task2.dagger.modules;

import com.alexkaz.task2.model.api.GitHubApi;
import com.alexkaz.task2.presenter.MainPresenter;
import com.alexkaz.task2.presenter.MainPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Provides
    MainPresenter provideMainPresenter(GitHubApi api){
        return new MainPresenterImpl(api);
    }

}
