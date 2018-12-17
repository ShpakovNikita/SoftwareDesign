package com.example.shaft.softwaredesign.rss.retrofit;

import com.example.shaft.softwaredesign.rss.model.Feed;
import com.google.firebase.database.core.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FeedAPI {
    @GET("news/.rss")
    Call<Feed> getFeed();
}
