package com.alexkaz.task2.view;

import com.alexkaz.task2.model.pojo.GitHubRepo;

import java.util.List;

public interface MainView {

    void showRepos(List<GitHubRepo> repos);

    void showAlertMessage(String message);

}
