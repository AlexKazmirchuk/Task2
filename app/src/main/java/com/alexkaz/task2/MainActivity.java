package com.alexkaz.task2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.alexkaz.task2.model.pojo.GitHubRepo;
import com.alexkaz.task2.presenter.MainPresenter;
import com.alexkaz.task2.util.RVRepoAdapter;
import com.alexkaz.task2.view.MainView;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainView {

    @Inject
    MainPresenter presenter;

    private RecyclerView rv;
    private RVRepoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        rv = findViewById(R.id.recycle_view);
        ((MyApp)getApplication()).getMainComponent().inject(this);
        presenter.bindView(this);

        adapter = new RVRepoAdapter();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        presenter.loadMore();
    }

    @Override
    public void showRepos(List<GitHubRepo> repos) {
        // todo
        adapter.add(repos);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showAlertMessage(String message) {
        // todo
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
