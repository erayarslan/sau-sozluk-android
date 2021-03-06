package com.guguluk.sausozluk.activity;

import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.guguluk.sausozluk.R;
import com.guguluk.sausozluk.adapter.TopicListAdapter;
import com.guguluk.sausozluk.dto.SearchResult;
import com.guguluk.sausozluk.dto.Topic;
import com.guguluk.sausozluk.dto.TopicListResult;
import com.guguluk.sausozluk.resource.TopicResource;
import com.guguluk.sausozluk.util.Constants;
import com.guguluk.sausozluk.util.Utils;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import uk.co.senab.actionbarpulltorefresh.extras.actionbarcompat.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;

public class TopicListActivity extends ActionBarActivity implements SearchView.OnQueryTextListener {

    private TopicResource topicResource = new TopicResource();
    private ListView topicListView;
    private PullToRefreshLayout pullToRefreshLayout;
    //
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_list);
        //
        Utils.startBugSense(this);
        Typeface font = Utils.getFont(this);
        //
        Utils.invisibleIcon(this);
        //
        pullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.ptr_layout);
        ActionBarPullToRefresh.from(this)
                .allChildrenArePullable()
                .listener(new OnRefreshListener() {
                    @Override
                    public void onRefreshStarted(View view) {
                        fetchTopics();
                    }
                })
                .setup(pullToRefreshLayout);
        //
        TextView title;
        if(findViewById(R.id.action_bar_title)!=null) {
            title = (TextView)findViewById(R.id.action_bar_title);
        } else {
            title = (TextView)findViewById(Utils.getVersionBasedTitleId(this));
        }
        title.setTypeface(font);
        title.setTextSize(Constants.title_font_size);
        //
        token = Utils.getUserToken(this);
        //
        topicListView = (ListView) findViewById(R.id.listTopic);
        //
        fetchTopics();
    }

    private boolean isServiceRun() {
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service : activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if(getApplication().getPackageName().equals(service.service.getPackageName())) {
                return true;
            }
        } return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.topic, menu);
        //
        MenuItem menuItem = menu.findItem(R.id.action_search);
        MenuItem loginItem = menu.findItem(R.id.action_login);
        MenuItem logoutItem = menu.findItem(R.id.action_logout);
        //
        if(token != null && !token.trim().equalsIgnoreCase(Constants.empty)) {
            loginItem.setVisible(false);
            logoutItem.setVisible(true);
        } else {
            loginItem.setVisible(true);
            logoutItem.setVisible(false);
        }
        //
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        //
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String input) {
        Intent intent = new Intent(this, EntryListActivity.class);
        intent.putExtra(Constants.topic_url_parameter, input);
        startActivity(intent);
        //
        return true;
    }

    @Override
    public boolean onQueryTextChange(String input) {
        if(input.trim().equalsIgnoreCase(Constants.empty)) {
            fetchTopics();
            return false;
        }
        pullToRefreshLayout.setRefreshing(true);
        topicResource.searchByTopic(input,new Callback() {
            @Override
            public void failure(RetrofitError arg0) {
                pullToRefreshLayout.setRefreshComplete();
                //
                Toast.makeText(TopicListActivity.this,arg0.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void success(Object arg0, Response arg1) {
                pullToRefreshLayout.setRefreshComplete();
                //
                final SearchResult searchResult = (SearchResult) arg0;
                final List<Topic> topicList = searchResult.getData().getTopics();
                //
                TopicListAdapter topicListAdapter = new TopicListAdapter(TopicListActivity.this, topicList);
                topicListView.setAdapter(topicListAdapter);
                topicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        Intent intent = new Intent(TopicListActivity.this, EntryListActivity.class);
                        intent.putExtra(Constants.topic_url_parameter, topicList.get(arg2).getName());
                        startActivity(intent);
                    }
                });
                //
                topicListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        Topic targetTopic = topicList.get(arg2);
                        //
                        String topic = targetTopic.getName();
                        String url = Constants.topic_base_url + targetTopic.getUrl();
                        String content = Utils.lowerCase(topic) + ": " + url + " " + Constants.twitter;
                        //
                        Utils.share(content,TopicListActivity.this);
                        //
                        return true;
                    }
                });
            }
        });
        //
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            Intent intent = new Intent(TopicListActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;
        } else if(id == R.id.action_login) {
            finish();
            Intent intent = new Intent(TopicListActivity.this, LoginActivity.class);
            startActivity(intent);
            return true;
        } else if(id == R.id.action_logout) {
            Utils.logout(this);
            finish();
            Intent intent = new Intent(TopicListActivity.this, TopicListActivity.class);
            startActivity(intent);
            return true;
        }
        //
        return super.onOptionsItemSelected(item);
    }

    private void fetchTopics() {
        pullToRefreshLayout.setRefreshing(true);
        topicResource.getTopicByCount(Constants.home_topic_count, new Callback() {
            @Override
            public void failure(RetrofitError arg0) {
                pullToRefreshLayout.setRefreshComplete();
                //
                Utils.showMessage(getString(R.string.error), arg0.getMessage(), TopicListActivity.this);
            }

            @Override
            public void success(Object arg0, Response arg1) {
                pullToRefreshLayout.setRefreshComplete();
                //
                final TopicListResult topicListResult = (TopicListResult) arg0;
                final List<Topic> topicList = topicListResult.getData();
                //
                TopicListAdapter topicListAdapter = new TopicListAdapter(TopicListActivity.this, topicList);
                topicListView.setAdapter(topicListAdapter);
                topicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        Intent intent = new Intent(TopicListActivity.this, EntryListActivity.class);
                        intent.putExtra(Constants.topic_url_parameter, topicList.get(arg2).getUrl());
                        startActivity(intent);
                    }
                });
                //
                topicListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        Topic targetTopic = topicList.get(arg2);
                        //
                        String topic = targetTopic.getName();
                        String url = Constants.topic_base_url + targetTopic.getUrl();
                        String content = Utils.lowerCase(topic) + ": " + url + " " + Constants.twitter;
                        //
                        Utils.share(content,TopicListActivity.this);
                        //
                        return true;
                    }
                });
            }
        });
    }
}
