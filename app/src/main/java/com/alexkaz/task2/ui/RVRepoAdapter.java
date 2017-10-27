package com.alexkaz.task2.ui;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alexkaz.task2.FullRepoActivity;
import com.alexkaz.task2.R;
import com.alexkaz.task2.model.pojo.GitHubRepo;
import com.alexkaz.task2.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class RVRepoAdapter extends RecyclerView.Adapter<RVRepoAdapter.UserRepoVH> {

    private List<GitHubRepo> repos = new ArrayList<>();

    public RVRepoAdapter() {
    }

    public void add(List<GitHubRepo> userRepos){
        if (userRepos != null){
            this.repos.addAll(userRepos);
        }
    }

    public List<GitHubRepo> getItems(){
        return repos;
    }

    public void clear(){
        repos.clear();
        notifyDataSetChanged();
    }

    @Override
    public UserRepoVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_repo_item, parent, false);
        return new UserRepoVH(v);
    }

    @Override
    public void onBindViewHolder(UserRepoVH holder, int position) {
        GitHubRepo item = repos.get(position);

        holder.langTV.setText(item.getLanguage());
        holder.titleTV.setText(item.getName());
        holder.descriptionTV.setText(item.getDescription());
        holder.forksTV.setText(Utils.formatNumber(item.getForksCount()));
        holder.starsTV.setText(Utils.formatNumber(item.getStargazersCount()));
        holder.updatedAt.setText(Utils.formatDate(item.getUpdatedAt()));
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    class UserRepoVH extends RecyclerView.ViewHolder {
        private TextView langTV,titleTV,descriptionTV,forksTV,starsTV, updatedAt;

        UserRepoVH(final View v) {
            super(v);

            langTV = v.findViewById(R.id.language);
            titleTV = v.findViewById(R.id.title);
            descriptionTV = v.findViewById(R.id.description);
            forksTV = v.findViewById(R.id.forks_amount);
            starsTV = v.findViewById(R.id.stars_amount);
            updatedAt = v.findViewById(R.id.updated_at);

            v.findViewById(R.id.repo_info_container).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), FullRepoActivity.class);
                    intent.putExtra("selected_repo", repos.get(UserRepoVH.this.getAdapterPosition()));
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

}
