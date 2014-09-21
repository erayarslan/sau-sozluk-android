package com.guguluk.sausozluk.resource;

import com.guguluk.sausozluk.service.GitHubContentService;
import com.guguluk.sausozluk.util.GitHubContentRestAdapter;

import retrofit.Callback;

@SuppressWarnings("unchecked")
public class GitHubContentResource {
    private GitHubContentService gitHubContentService;

    public GitHubContentResource() {
        gitHubContentService = new GitHubContentRestAdapter().getRestAdapter().create(GitHubContentService.class);
    }

    public void getProjectReadMe(Callback callback) {
        gitHubContentService.getProjectReadMe(callback);
    }
}
