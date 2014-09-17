package com.guguluk.sausozluk.service;

import com.guguluk.sausozluk.dto.EntryListResult;
import com.guguluk.sausozluk.dto.EntryResult;
import com.guguluk.sausozluk.dto.StatsResult;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface EntryService {
    @GET("/entries/{topicUrl}/{page}")
    void getTopicEntriesByPage(@Path("topicUrl") String topicUrl, @Path("page") Integer page, Callback<EntryListResult> callback);

    @GET("/votes/{id}")
    void getEntryStatsById(@Path("id") String id, Callback<StatsResult> callback);

    @GET("/entry/{id}")
    void getEntryById(@Path("id") String id, Callback<EntryResult> callback);
}
