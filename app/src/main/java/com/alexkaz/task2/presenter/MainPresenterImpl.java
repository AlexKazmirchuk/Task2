package com.alexkaz.task2.presenter;

import com.alexkaz.task2.model.api.GitHubApi;
import com.alexkaz.task2.model.pojo.GitHubRepo;
import com.alexkaz.task2.view.MainView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenterImpl implements MainPresenter {

    public static final int PER_PAGE = 10;

    private GitHubApi api;
    private MainView view;

    private int page = 1;
    private Call<List<GitHubRepo>>  call;

    public MainPresenterImpl(GitHubApi api) {
        this.api = api;
    }

    @Override
    public void bindView(MainView view) {
        this.view = view;
    }

    @Override
    public void loadMore() {
        if (view != null && api != null){
            view.showPaginateError(false);
            view.showPaginateLoading(true);
            call = api.getUserRepos(page,PER_PAGE);
            call.enqueue(new Callback<List<GitHubRepo>>() {
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
        }
    }

    @Override
    public void setPage(int page) {
        if (page > 1)
            this.page = page;
        else
            this.page = 1;
    }

    @Override
    public void cancelLoading() {
        if (call != null && !call.isCanceled()){
            call.cancel();
        }
    }
}
