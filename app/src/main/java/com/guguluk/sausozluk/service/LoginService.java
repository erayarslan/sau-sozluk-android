package com.guguluk.sausozluk.service;

import com.guguluk.sausozluk.dto.LoginResponse;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface LoginService {

    @FormUrlEncoded
    @POST("/login")
    void login(@Field("username") String username, @Field("password") String password, Callback<LoginResponse> callback);
}
