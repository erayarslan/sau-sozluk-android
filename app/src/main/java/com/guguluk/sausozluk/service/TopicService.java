package com.guguluk.sausozluk.service;

import com.guguluk.sausozluk.dto.CreateTopicResult;
import com.guguluk.sausozluk.dto.SearchResult;
import com.guguluk.sausozluk.dto.TopicListResult;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;

public interface TopicService {
    @GET("/topics/{count}")
    void getTopicByCount(@Path("count") Integer count, Callback<TopicListResult> callback);

    @GET("/search/{query}")
    void searchByTopic(@Path("query") String query, Callback<SearchResult> callback);

    @FormUrlEncoded
    @POST("/create/topic")
    void createTopic(@Header("stoken") String stoken, @Field("name") String name, @Field("url") String url, @Field("createdBy") String createdBy, Callback<CreateTopicResult> callback);
}
