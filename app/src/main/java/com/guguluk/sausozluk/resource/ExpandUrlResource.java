package com.guguluk.sausozluk.resource;

import com.guguluk.sausozluk.dto.Expand;
import com.guguluk.sausozluk.service.ExpandUrlService;
import com.guguluk.sausozluk.service.GitHubContentService;
import com.guguluk.sausozluk.util.ExpandUrlRestAdapter;
import com.guguluk.sausozluk.util.GitHubContentRestAdapter;

import retrofit.Callback;

@SuppressWarnings("unchecked")
public class ExpandUrlResource {
    private ExpandUrlService expandUrlService;

    public ExpandUrlResource() {
        expandUrlService = new ExpandUrlRestAdapter().getRestAdapter().create(ExpandUrlService.class);
    }

    public Expand getProjectReadMe(String url) {
        return expandUrlService.getExpandUrl(url);
    }
}
