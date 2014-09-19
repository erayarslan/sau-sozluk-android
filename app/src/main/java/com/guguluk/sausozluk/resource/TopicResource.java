package com.guguluk.sausozluk.resource;

import com.guguluk.sausozluk.service.TopicService;
import com.guguluk.sausozluk.util.SauSozlukRestAdapter;
import retrofit.Callback;

@SuppressWarnings("unchecked")
public class TopicResource {
    private TopicService topicService;

    public TopicResource() {
        topicService = new SauSozlukRestAdapter().getRestAdapter().create(TopicService.class);
    }

    public void getTopicByCount(Integer count, Callback callback) {
        topicService.getTopicByCount(count, callback);
    }

    public void searchByTopic(String query, Callback callback) {
        topicService.searchByTopic(query, callback);
    }
}
