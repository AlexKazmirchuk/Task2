package com.alexkaz.task2;

import com.alexkaz.task2.model.api.GitHubApi;
import com.alexkaz.task2.model.pojo.GitHubRepo;
import com.alexkaz.task2.presenter.MainPresenterImpl;
import com.alexkaz.task2.view.MainView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class TestMainPresenterImpl {

    private OkHttpClient client;
    private OfflineMockInterceptor mockInterceptor;

    private GitHubApi api;

    @Mock
    GitHubApi mockApi;

    @Mock
    MainView view;

    @Mock
    Call<List<GitHubRepo>> mockedCall;

    private MainPresenterImpl presenter;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

        mockInterceptor = new OfflineMockInterceptor("");
        client = new OkHttpClient.Builder()
                .addInterceptor(mockInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GitHubApi.END_POINT)
                .addConverterFactory(GsonConverterFactory.create())
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .client(client)
                .build();

        api = retrofit.create(GitHubApi.class);

        when(mockApi.getUserRepos(anyInt(),anyInt())).thenReturn(mockedCall);
    }

    @Test
    public void test_loadMore_with_valid_response_body_from_file() throws IOException, InterruptedException {
        mockInterceptor.setResponseBody(getBodyFromFile("test_responses/body.json")).setCode(200);

        presenter = new MainPresenterImpl(api);
        presenter.bindView(view);
        presenter.loadMore();

        Thread.sleep(1000);

        verify(view).showPaginateError(false);
        verify(view).showPaginateLoading(true);
        verify(view).showPaginateLoading(false);
        verify(view).showRepos(Mockito.<GitHubRepo>anyList());

        verify(view, never()).showPaginateError(true);
        verify(view, never()).setPaginateNoMoreData(anyBoolean());
        verify(view, never()).showAlertMessage(anyString());

        clearInvocations(view);
    }

    @Test
    public void test_loadMore_with_valid_but_cutted_response_body_from_file() throws IOException, InterruptedException {
        mockInterceptor.setResponseBody(getBodyFromFile("test_responses/cutted_body.json")).setCode(200);

        presenter = new MainPresenterImpl(api);
        presenter.bindView(view);
        presenter.loadMore();

        Thread.sleep(1000);

        verify(view).showPaginateError(false);
        verify(view).showPaginateLoading(true);
        verify(view).showPaginateLoading(false);
        verify(view).showRepos(Mockito.<GitHubRepo>anyList());
        verify(view).setPaginateNoMoreData(true);

        verify(view, never()).setPaginateNoMoreData(false);
        verify(view, never()).showPaginateError(true);
        verify(view, never()).showAlertMessage(anyString());

        clearInvocations(view);
    }

    @Test
    public void test_loadMore_with_valid_empty_response_body() throws IOException, InterruptedException {
        mockInterceptor.setResponseBody("[]").setCode(200);

        presenter = new MainPresenterImpl(api);
        presenter.bindView(view);
        presenter.loadMore();

        Thread.sleep(1000);

        verify(view).showPaginateError(false);
        verify(view).showPaginateLoading(true);
        verify(view).showPaginateLoading(false);
        verify(view).showRepos(Mockito.<GitHubRepo>anyList());
        verify(view).setPaginateNoMoreData(true);

        verify(view, never()).setPaginateNoMoreData(false);
        verify(view, never()).showPaginateError(true);
        verify(view, never()).showAlertMessage(anyString());

        clearInvocations(view);
    }

    @Test
    public void test_loadMore_with_invalid_code() throws IOException, InterruptedException {
        mockInterceptor.setResponseBody(getBodyFromFile("test_responses/body.json"))
                .setCode(404)
                .setErrorBody("Some error occured");

        presenter = new MainPresenterImpl(api);
        presenter.bindView(view);
        presenter.loadMore();

        Thread.sleep(1000);

        verify(view).showPaginateError(false);
        verify(view).showPaginateLoading(true);
        verify(view).showPaginateLoading(false);
        verify(view).showPaginateError(true);
        verify(view).showAlertMessage("Some error occured");

        verify(view, never()).showRepos(Mockito.<GitHubRepo>anyList());
        verify(view, never()).setPaginateNoMoreData(anyBoolean());
        verify(view).showAlertMessage(anyString());

        clearInvocations(view);
    }

    @Test
    public void test_loadMore_when_exceptions_occured() throws InterruptedException {
        mockInterceptor.setResponseBody("some wrong unparselable body")
                .setCode(404)
                .setErrorBody("Some error occured");

        presenter = new MainPresenterImpl(api);
        presenter.bindView(view);
        presenter.loadMore();

        Thread.sleep(1000);

        verify(view).showPaginateError(false);
        verify(view).showPaginateLoading(true);
        verify(view).showPaginateLoading(false);
        verify(view).showPaginateError(true);

        verify(view, never()).showRepos(Mockito.<GitHubRepo>anyList());
        verify(view, never()).setPaginateNoMoreData(anyBoolean());
        verify(view).showAlertMessage(anyString());

        clearInvocations(view);
    }

    @Test
    public void test_loadMore_with_mocked_api(){
        presenter = new MainPresenterImpl(mockApi);
        presenter.bindView(view);
        presenter.loadMore();

        verify(view).showPaginateError(false);
        verify(view).showPaginateLoading(true);
        verify(mockApi).getUserRepos(1,10);
        verify(mockApi).getUserRepos(anyInt(), anyInt());
        verify(mockedCall).enqueue((Callback<List<GitHubRepo>>) any());

        verify(view, never()).setPaginateNoMoreData(anyBoolean());
        verify(view, never()).showAlertMessage(anyString());
        verify(view, never()).showPaginateLoading(false);
        verify(view, never()).showPaginateError(true);

        clearInvocations(view, mockApi, mockedCall);
    }

    @Test
    public void test_loadMore_with_mocked_api_called_2_times(){
        presenter = new MainPresenterImpl(mockApi);
        presenter.bindView(view);

        presenter.loadMore();
        presenter.loadMore();

        verify(view, times(2)).showPaginateError(false);
        verify(view, times(2)).showPaginateLoading(true);
        verify(mockApi, times(2)).getUserRepos(1,10);
        verify(mockApi, never()).getUserRepos(2,10);
        verify(mockApi, times(2)).getUserRepos(anyInt(), anyInt());
        verify(mockedCall, times(2)).enqueue((Callback<List<GitHubRepo>>) any());

        verify(view, never()).setPaginateNoMoreData(anyBoolean());
        verify(view, never()).showAlertMessage(anyString());
        verify(view, never()).showPaginateLoading(false);
        verify(view, never()).showPaginateError(true);

        clearInvocations(view, mockApi, mockedCall);
    }

    @Test
    public void test_loadMore_with_mocked_api_called_2_times_and_different_page(){
        presenter = new MainPresenterImpl(mockApi);
        presenter.bindView(view);

        presenter.loadMore();
        presenter.setPage(2);
        presenter.loadMore();

        verify(view, times(2)).showPaginateError(false);
        verify(view, times(2)).showPaginateLoading(true);
        verify(mockApi, times(1)).getUserRepos(1,10);
        verify(mockApi, times(1)).getUserRepos(2,10);
        verify(mockApi, times(2)).getUserRepos(anyInt(), anyInt());
        verify(mockedCall, times(2)).enqueue((Callback<List<GitHubRepo>>) any());

        verify(view, never()).setPaginateNoMoreData(anyBoolean());
        verify(view, never()).showAlertMessage(anyString());
        verify(view, never()).showPaginateLoading(false);
        verify(view, never()).showPaginateError(true);

        clearInvocations(view, mockApi, mockedCall);
    }

    @Test
    public void test_loadMore_with_mocked_api_called_2_times_and_different_pages(){
        presenter = new MainPresenterImpl(mockApi);
        presenter.bindView(view);

        presenter.setPage(5);
        presenter.loadMore();
        presenter.setPage(10);
        presenter.loadMore();

        verify(view, times(2)).showPaginateError(false);
        verify(view, times(2)).showPaginateLoading(true);
        verify(mockApi, never()).getUserRepos(1,10);
        verify(mockApi, never()).getUserRepos(2,10);
        verify(mockApi, times(1)).getUserRepos(5,10);
        verify(mockApi, times(1)).getUserRepos(10,10);
        verify(mockApi, times(2)).getUserRepos(anyInt(), anyInt());
        verify(mockedCall, times(2)).enqueue((Callback<List<GitHubRepo>>) any());

        verify(view, never()).setPaginateNoMoreData(anyBoolean());
        verify(view, never()).showAlertMessage(anyString());
        verify(view, never()).showPaginateLoading(false);
        verify(view, never()).showPaginateError(true);

        clearInvocations(view, mockApi, mockedCall);
    }

    @Test
    public void test_loadMore_with_nullable_view_and_api(){
        presenter = new MainPresenterImpl(mockApi);
        presenter.loadMore();

        verifyZeroInteractions(view, mockApi);

        presenter = new MainPresenterImpl(null);
        presenter.loadMore();

        verifyZeroInteractions(view, mockApi);

        presenter = new MainPresenterImpl(null);
        presenter.bindView(view);
        presenter.loadMore();

        verifyZeroInteractions(view, mockApi);
    }

    @Test
    public void test_cancelLoading(){
        presenter = new MainPresenterImpl(mockApi);
        presenter.bindView(view);

        presenter.cancelLoading();

        verify(mockedCall, never()).cancel();

        presenter.loadMore();
        presenter.cancelLoading();

        verify(mockedCall).cancel();

        clearInvocations(view, mockApi, mockedCall);
    }

    @Test
    public void test_setPage(){
        presenter = new MainPresenterImpl(mockApi);
        presenter.bindView(view);

        presenter.loadMore();

        verify(mockApi).getUserRepos(1,10);
        verify(mockApi).getUserRepos(anyInt(),anyInt());

        presenter.setPage(3);
        presenter.loadMore();

        verify(mockApi).getUserRepos(3,10);
        verify(mockApi, times(2)).getUserRepos(anyInt(),anyInt());

        presenter.setPage(25);
        presenter.loadMore();

        verify(mockApi).getUserRepos(25,10);
        verify(mockApi, times(3)).getUserRepos(anyInt(),anyInt());

        presenter.setPage(-45);
        presenter.loadMore();

        verify(mockApi, times(2)).getUserRepos(1,10);
        verify(mockApi, times(4)).getUserRepos(anyInt(),anyInt());

        presenter.setPage(0);
        presenter.loadMore();

        verify(mockApi, times(3)).getUserRepos(1,10);
        verify(mockApi, times(5)).getUserRepos(anyInt(),anyInt());

        clearInvocations(view, mockApi, mockedCall);
    }

    private String getBodyFromFile(String path) throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(
                                new File(path))));
        StringBuilder result = new StringBuilder("");
        String buff = "";

        while ((buff = reader.readLine()) != null){
            result.append(buff);
        }

        return result.toString();
    }

}
