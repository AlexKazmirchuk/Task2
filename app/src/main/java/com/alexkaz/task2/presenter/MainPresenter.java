package com.alexkaz.task2.presenter;

import com.alexkaz.task2.view.MainView;

public interface MainPresenter {

    void bindView(MainView view);

    void loadMore();

    void setPage(int page);

    void onDestroy();

}
