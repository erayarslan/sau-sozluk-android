package com.guguluk.sausozluk.service;

import com.guguluk.sausozluk.dto.UserResult;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface UserService {
    @GET("/user/{username}")
    void getUserByUsername(@Path("username") String username, Callback<UserResult> callback);
}
