package com.guguluk.sausozluk.util;

import retrofit.RestAdapter;

public class GitHubContentRestAdapter {
    private RestAdapter restAdapter;

    public GitHubContentRestAdapter() {
        restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://raw.githubusercontent.com")
                .setConverter(new StringConverter())
                .build();
    }

    public RestAdapter getRestAdapter() {
        return this.restAdapter;
    }
}
