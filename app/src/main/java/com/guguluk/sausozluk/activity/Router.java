package com.guguluk.sausozluk.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import com.guguluk.sausozluk.dto.Expand;
import com.guguluk.sausozluk.resource.ExpandUrlResource;
import com.guguluk.sausozluk.util.Constants;

public class Router extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String url = getIntent().getData().toString();
        Intent intent = checkCommon(url);
        if (intent!=null) {
            startActivity(intent);
            finish();
        } else {
            // todo : Twitter t.co Mode ;)
            ExpandUrlResource expandUrlResource = new ExpandUrlResource();
            Expand expand = expandUrlResource.getProjectReadMe(url);
            Intent newIntent = checkCommon(expand.getEnd_url());
            if(newIntent!=null) {
                startActivity(newIntent);
                finish();
            } else {
                finish();
            }
        }
    }

    private Intent checkCommon(String url) {
        Intent intent;
        if(url.startsWith(Constants.topic_base_url)){
            intent = new Intent(this, EntryListActivity.class);
            intent.putExtra(Constants.topic_url_parameter, url.split("/")[5]);
            return intent;
        } else if(url.startsWith(Constants.entry_base_url)) {
            intent = new Intent(this, EntryListActivity.class);
            intent.putExtra(Constants.entry_id_parameter, url.split("/")[5]);
            return intent;
        } else if(url.startsWith(Constants.user_base_url)) {
            intent = new Intent(this, ProfileActivity.class);
            intent.putExtra(Constants.username_parameter, url.split("/")[5]);
            return intent;
        } return null;
    }
}
