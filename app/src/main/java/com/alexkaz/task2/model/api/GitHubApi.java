package com.alexkaz.task2.model.api;

import com.alexkaz.task2.model.pojo.GitHubRepo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GitHubApi {

    String END_POINT = "https://api.github.com/";

    String CLIENT_ID = "client_id=61596873f6b9905c0cba";
    String CLIENT_SECRET = "client_secret=88a821fb26d31d2a73df042a0dd57f38e6b40424";
    String WITH_ID_AND_SECRET = "?" + CLIENT_ID + "&" + CLIENT_SECRET;

    @GET("users/JakeWharton/repos" + WITH_ID_AND_SECRET)
    Call<List<GitHubRepo>> getUserRepos(@Query("page") int page, @Query("per_page") int perPage);
}
