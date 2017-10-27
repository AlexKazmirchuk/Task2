package com.alexkaz.task2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.alexkaz.task2.model.pojo.GitHubRepo;
import com.alexkaz.task2.util.Utils;

public class FullRepoActivity extends AppCompatActivity {

    private TextView title, description, starsAmount, forksAmount, watchersAmount, issuesAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_repo);

        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        starsAmount = findViewById(R.id.stars_amount);
        forksAmount = findViewById(R.id.forks_amount);
        watchersAmount = findViewById(R.id.watchers_amount);
        issuesAmount = findViewById(R.id.issues_amount);

        GitHubRepo repo;

        try {
            repo = getIntent().getParcelableExtra("selected_repo");
            if (repo != null)
                setValues(repo);
        } catch (ClassCastException e){
            Log.e(FullRepoActivity.class.getName(), e.getMessage());
        }

    }

    private void setValues(GitHubRepo repo){
        title.setText(!TextUtils.isEmpty(repo.getName()) ? repo.getName() : repo.getFullName());

        description.setText(repo.getDescription());

        starsAmount.setText(Utils.formatNumber(repo.getStargazersCount()));
        forksAmount.setText(Utils.formatNumber(repo.getForksCount()));
        watchersAmount.setText(Utils.formatNumber(repo.getWatchersCount()));
        issuesAmount.setText(Utils.formatNumber(repo.getIssuesCount()));
    }
}
