package com.guguluk.sausozluk.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Date;

import retrofit.RestAdapter;

public class GitHubContentRestAdapter {
    private RestAdapter restAdapter;

    public GitHubContentRestAdapter() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();

        restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://raw.githubusercontent.com")
                .setConverter(new StringConverter())
                .build();
    }

    public RestAdapter getRestAdapter() {
        return this.restAdapter;
    }
}
