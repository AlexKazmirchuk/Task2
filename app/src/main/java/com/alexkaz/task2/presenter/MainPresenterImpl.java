package com.alexkaz.task2.presenter;

import com.alexkaz.task2.model.api.GitHubApi;
import com.alexkaz.task2.model.pojo.GitHubRepo;
import com.alexkaz.task2.util.ConnInfoHelper;
import com.alexkaz.task2.view.MainView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenterImpl implements MainPresenter {

    private MainView view;
    private ConnInfoHelper connInfo;
    private GitHubApi api;

    public MainPresenterImpl(ConnInfoHelper connInfo, GitHubApi api) {
        this.connInfo = connInfo;
        this.api = api;
    }

    @Override
    public void bindView(MainView view) {
        this.view = view;
    }

    @Override
    public void loadMore() {
        //todo
        if(connInfo.isOnline()){
            api.getUserRepos(0,10).enqueue(new Callback<List<GitHubRepo>>() {
                @Override
                public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {
                    if (response.isSuccessful()){
                        view.showRepos(response.body());
                    } else {
                        view.showAlertMessage(response.message());
                    }
                }

                @Override
                public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {
                    view.showAlertMessage(t.getMessage());
                }
            });
        } else {
            view.showAlertMessage("No internet!");
        }
    }
}
