package com.alexkaz.task2.view;

import com.alexkaz.task2.model.pojo.GitHubRepo;

import java.util.List;

import ru.alexbykov.nopaginate.callback.PaginateView;

public interface MainView extends PaginateView {

    void showRepos(List<GitHubRepo> repos);

    void showAlertMessage(String message);

}
