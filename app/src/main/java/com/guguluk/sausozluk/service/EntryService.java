package com.guguluk.sausozluk.service;

import com.guguluk.sausozluk.dto.EntryListResult;
import com.guguluk.sausozluk.dto.EntryResult;
import com.guguluk.sausozluk.dto.StatsResult;
import com.guguluk.sausozluk.dto.Status;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;

public interface EntryService {
    @GET("/entries/{topicUrl}/{page}")
    void getTopicEntriesByPage(@Path("topicUrl") String topicUrl, @Path("page") Integer page, Callback<EntryListResult> callback);

    @GET("/votes/{id}")
    void getEntryStatsById(@Path("id") String id, Callback<StatsResult> callback);

    @GET("/entry/{id}")
    void getEntryById(@Path("id") String id, Callback<EntryResult> callback);

    @FormUrlEncoded
    @POST("/create/entry")
    void createEntry(@Header("stoken") String stoken, @Field("text") String text, @Field("createdBy") String createdBy, @Field("topic") String topic, Callback<Status> callback);
}
