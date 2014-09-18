package com.guguluk.sausozluk.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import com.guguluk.sausozluk.util.Constants;

public class Router extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String url = getIntent().getData().toString();
        Intent intent;
        if(url.startsWith(Constants.topic_base_url)){
            intent = new Intent(this, EntryListActivity.class);
            intent.putExtra(Constants.topic_url_parameter, url.split("/")[5]);
        } else if(url.startsWith(Constants.entry_base_url)) {
            intent = new Intent(this, EntryListActivity.class);
            intent.putExtra(Constants.entry_id_parameter, url.split("/")[5]);
        } else if(url.startsWith(Constants.user_base_url)) {
            intent = new Intent(this, ProfileActivity.class);
            intent.putExtra(Constants.username_parameter, url.split("/")[5]);
        }
        else {
            intent = new Intent(this, TopicListActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
