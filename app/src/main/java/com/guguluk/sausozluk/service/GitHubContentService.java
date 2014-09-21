package com.guguluk.sausozluk.service;

import retrofit.Callback;
import retrofit.http.GET;

public interface GitHubContentService {
    @GET("/erayarslan/sau-sozluk-android/master/app/src/main/assets/about.html")
    void getProjectReadMe(Callback<String> callback);
}
