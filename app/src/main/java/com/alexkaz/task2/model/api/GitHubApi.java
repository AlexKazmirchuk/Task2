package com.alexkaz.task2.model.api;

import com.alexkaz.task2.model.pojo.GitHubRepo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GitHubApi {

    String END_POINT = "https://api.github.com/";

    @GET("users/JakeWharton/repos")
    Call<List<GitHubRepo>> getUserRepos(@Query("page") int page, @Query("per_page") int perPage);
}
