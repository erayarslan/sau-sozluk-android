package com.guguluk.sausozluk.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Date;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class GitHubContentRestAdapter {
    private RestAdapter restAdapter;

    public GitHubContentRestAdapter() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();

        restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://sausozluk.net")
                .setConverter(new GsonConverter(gson))
                .build();
    }

    public RestAdapter getRestAdapter() {
        return this.restAdapter;
    }
}
