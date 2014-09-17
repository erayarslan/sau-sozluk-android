package com.guguluk.sausozluk.service;

import com.guguluk.sausozluk.dto.TopicListResult;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface TopicService {
    @GET("/topics/{count}")
    void getTopicByCount(@Path("count") Integer count, Callback<TopicListResult> callback);
}
