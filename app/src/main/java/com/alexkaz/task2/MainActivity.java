package com.alexkaz.task2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.alexkaz.task2.model.pojo.GitHubRepo;
import com.alexkaz.task2.presenter.MainPresenter;
import com.alexkaz.task2.presenter.MainPresenterImpl;
import com.alexkaz.task2.ui.CustomLoadingItem;
import com.alexkaz.task2.ui.RVRepoAdapter;
import com.alexkaz.task2.view.MainView;

import java.util.List;

import javax.inject.Inject;

import ru.alexbykov.nopaginate.callback.OnLoadMore;
import ru.alexbykov.nopaginate.paginate.Paginate;
import ru.alexbykov.nopaginate.paginate.PaginateBuilder;

public class MainActivity extends AppCompatActivity implements MainView {

    @Inject
    MainPresenter presenter;

    private RecyclerView rv;
    private RVRepoAdapter adapter;
    private Paginate paginate;

    private boolean hasMoreItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupPresenter();
        setupRecyclerView();
        setupPaginate();
    }

    private void setupPresenter(){
        ((MyApp)getApplication()).getMainComponent().inject(this);
        presenter.bindView(this);
    }

    private void setupRecyclerView(){
        adapter = new RVRepoAdapter();
        rv = findViewById(R.id.recycle_view);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        Object o = getLastCustomNonConfigurationInstance();
        if (o != null) {
            List<GitHubRepo> items = (List<GitHubRepo>) o;
            adapter.add(items);
            int page = items.size() / MainPresenterImpl.PER_PAGE;
            presenter.setPage(page != 0 ? page : 1);
        }
    }

    private void setupPaginate() {
         paginate = new PaginateBuilder()
                .with(rv)
                .setCallback(new OnLoadMore() {
                    @Override
                    public void onLoadMore() {
                        presenter.loadMore();
                    }
                })
                .setCustomLoadingItem(new CustomLoadingItem())
                .setLoadingTriggerThreshold(5)
                .build();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("hasMoreItems", hasMoreItems);
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        setPaginateNoMoreData(state.getBoolean("hasMoreItems"));
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return adapter.getItems();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        paginate.unSubscribe();
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

    @Override
    public void showPaginateLoading(boolean show) {
        paginate.showLoading(show);
    }

    @Override
    public void showPaginateError(boolean show) {
        paginate.showError(show);
    }

    @Override
    public void setPaginateNoMoreData(boolean hasMoreItems) {
        this.hasMoreItems = hasMoreItems;
        paginate.setPaginateNoMoreItems(hasMoreItems);
    }
}
