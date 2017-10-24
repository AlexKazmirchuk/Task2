package com.alexkaz.task2.util;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alexkaz.task2.R;
import com.alexkaz.task2.model.pojo.GitHubRepo;

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
        holder.forksTV.setText(String.valueOf(item.getForksCount()));
        holder.starsTV.setText(String.valueOf(item.getStargazersCount()));
        holder.updatedAt.setText(item.getUpdatedAt());
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    static class UserRepoVH extends RecyclerView.ViewHolder {
        private TextView langTV,titleTV,descriptionTV,forksTV,starsTV, updatedAt;

        UserRepoVH(View v) {
            super(v);

            langTV = v.findViewById(R.id.langTV);
            titleTV = v.findViewById(R.id.titleTV);
            descriptionTV = v.findViewById(R.id.descriptionTV);
            forksTV = v.findViewById(R.id.forksTV);
            starsTV = v.findViewById(R.id.starsTV);
            updatedAt = v.findViewById(R.id.updated_at);
        }
    }

}
