package com.guguluk.sausozluk.resource;

import com.guguluk.sausozluk.service.EntryService;
import com.guguluk.sausozluk.util.SauSozlukRestAdapter;

import retrofit.Callback;

@SuppressWarnings("unchecked")
public class GitHubContentResource {
    private EntryService entryService;

    public GitHubContentResource() {
        entryService = new SauSozlukRestAdapter().getRestAdapter().create(EntryService.class);
    }

    public void getTopicEntriesByPage(String topicUrl, Integer page, Callback callback) {
        entryService.getTopicEntriesByPage(topicUrl, page, callback);
    }

    public void getEntryStatsById(String id, Callback callback) {
        entryService.getEntryStatsById(id, callback);
    }

    public void getEntryById(String id, Callback callback) {
        entryService.getEntryById(id, callback);
    }
}
