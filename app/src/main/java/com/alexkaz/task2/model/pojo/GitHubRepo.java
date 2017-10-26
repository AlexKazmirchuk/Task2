package com.alexkaz.task2.model.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class GitHubRepo implements Parcelable {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("full_name")
    private String fullName;

    @SerializedName("description")
    private String description;

    @SerializedName("stargazers_count")
    private int stargazersCount;

    @SerializedName("watchers_count")
    private int watchersCount;

    @SerializedName("forks_count")
    private int forksCount;

    @SerializedName("open_issues_count")
    private int issuesCount;

    @SerializedName("language")
    private String language;

    @SerializedName("updated_at")
    private String updatedAt;

    public GitHubRepo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStargazersCount() {
        return stargazersCount;
    }

    public void setStargazersCount(int stargazersCount) {
        this.stargazersCount = stargazersCount;
    }

    public int getWatchersCount() {
        return watchersCount;
    }

    public void setWatchersCount(int watchersCount) {
        this.watchersCount = watchersCount;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getForksCount() {
        return forksCount;
    }

    public void setForksCount(int forksCount) {
        this.forksCount = forksCount;
    }

    public int getIssuesCount() {
        return issuesCount;
    }

    public void setIssuesCount(int issuesCount) {
        this.issuesCount = issuesCount;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    protected GitHubRepo(Parcel in) {
        id = in.readInt();
        name = in.readString();
        fullName = in.readString();
        description = in.readString();
        stargazersCount = in.readInt();
        watchersCount = in.readInt();
        forksCount = in.readInt();
        issuesCount = in.readInt();
        language = in.readString();
        updatedAt = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(fullName);
        dest.writeString(description);
        dest.writeInt(stargazersCount);
        dest.writeInt(watchersCount);
        dest.writeInt(forksCount);
        dest.writeInt(issuesCount);
        dest.writeString(language);
        dest.writeString(updatedAt);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<GitHubRepo> CREATOR = new Parcelable.Creator<GitHubRepo>() {
        @Override
        public GitHubRepo createFromParcel(Parcel in) {
            return new GitHubRepo(in);
        }

        @Override
        public GitHubRepo[] newArray(int size) {
            return new GitHubRepo[size];
        }
    };
}