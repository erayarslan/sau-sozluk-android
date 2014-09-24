package com.guguluk.sausozluk.service;

import com.guguluk.sausozluk.dto.Expand;

import retrofit.http.GET;
import retrofit.http.Query;

public interface ExpandUrlService {
    @GET("/expand")
    Expand getExpandUrl(@Query("url") String url);
}
