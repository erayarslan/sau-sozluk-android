package com.guguluk.sausozluk.resource;

import com.guguluk.sausozluk.service.EntryService;
import com.guguluk.sausozluk.util.SauSozlukRestAdapter;

import retrofit.Callback;

@SuppressWarnings("unchecked")
public class EntryResource {
    private EntryService entryService;

    public EntryResource() {
        entryService = new SauSozlukRestAdapter().getRestAdapter().create(EntryService.class);
    }

    public void getTopicEntriesByPage(String topicUrl, Integer page, Callback callback) {
        entryService.getTopicEntriesByPage(topicUrl, page, callback);
    }

    public void getEntryStatsById(String stoken, String id, Callback callback) {
        entryService.getEntryStatsById(stoken, id, callback);
    }

    public void getEntryById(String id, Callback callback) {
        entryService.getEntryById(id, callback);
    }

    public void createEntry(String stoken, String text, String createdBy, String topic, Callback callback) {
        entryService.createEntry(stoken, text, createdBy, topic, callback);
    }
}
