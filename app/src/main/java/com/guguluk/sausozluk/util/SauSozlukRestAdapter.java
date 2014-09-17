package com.guguluk.sausozluk.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import java.util.Date;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class SauSozlukRestAdapter {
    private RestAdapter restAdapter;

    public SauSozlukRestAdapter() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();

        restAdapter = new retrofit.RestAdapter.Builder()
                .setEndpoint("http://sausozluk.net")
                .setConverter(new GsonConverter(gson))
                .build();
    }

    public RestAdapter getRestAdapter() {
        return this.restAdapter;
    }
}
