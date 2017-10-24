package com.alexkaz.task2.presenter;

import android.util.Log;

import com.alexkaz.task2.model.api.GitHubApi;
import com.alexkaz.task2.model.pojo.GitHubRepo;
import com.alexkaz.task2.util.ConnInfoHelper;
import com.alexkaz.task2.view.MainView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenterImpl implements MainPresenter {

    public static final int PER_PAGE = 10;

    private MainView view;
    private ConnInfoHelper connInfo;

    private GitHubApi api;

    private int page = 0;

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
        Log.d("myTag", "loadMore " + page);
        if(connInfo.isOnline()){
            view.showPaginateError(false);
            view.showPaginateLoading(true);
            api.getUserRepos(page,PER_PAGE).enqueue(new Callback<List<GitHubRepo>>() {
                @Override
                public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {
                    if (response.isSuccessful()){
                        view.showPaginateLoading(false);
                        view.showRepos(response.body());
                        page++;

                        if (response.body().size() == 0 || response.body().size() < PER_PAGE){
                            view.setPaginateNoMoreData(true);
                        }

                    } else {
                        view.showAlertMessage(response.message());
                        view.showPaginateLoading(false);
                        view.showPaginateError(true);
                    }
                }

                @Override
                public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {
                    view.showAlertMessage(t.getMessage());
                    view.showPaginateLoading(false);
                    view.showPaginateError(true);
                }
            });
        } else {
            view.showAlertMessage("No internet!");
            view.showPaginateLoading(false);
            view.showPaginateError(true);
        }
    }

    @Override
    public void setPage(int page) {
        this.page = page;
    }
}
