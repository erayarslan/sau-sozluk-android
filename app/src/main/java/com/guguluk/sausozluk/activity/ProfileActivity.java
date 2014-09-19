package com.guguluk.sausozluk.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Build;

import com.guguluk.sausozluk.R;
import com.guguluk.sausozluk.adapter.LastFiveEntryListAdapter;
import com.guguluk.sausozluk.dto.User;
import com.guguluk.sausozluk.dto.UserResult;
import com.guguluk.sausozluk.resource.UserResource;
import com.guguluk.sausozluk.util.Constants;
import com.guguluk.sausozluk.util.Utils;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ProfileActivity extends ActionBarActivity {

    UserResource userResource = new UserResource();
    //
    TextView username;
    TextView auth;
    TextView message;
    TextView generation;
    TextView entry_count;
    TextView point;
    TextView last_active_value;
    TextView registered_value;
    //
    private ListView entryListView;
    //
    private String userUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_profile);
        //
        Typeface font = Utils.getFont(this);
        //
        TextView title;
        if(findViewById(R.id.action_bar_title)!=null) {
            title = (TextView)findViewById(R.id.action_bar_title);
        } else {
            title = (TextView)findViewById(Utils.getVersionBasedTitleId(this));
        }
        title.setTypeface(font);
        title.setTextSize(Constants.title_font_size); // todo: noktakomm was here
        
        if(Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD)
            getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        
        //
        entryListView = (ListView) findViewById(R.id.listFiveEntry);
        //
        username = (TextView) findViewById(R.id.username);
        auth = (TextView) findViewById(R.id.auth);
        message = (TextView) findViewById(R.id.message);
        generation = (TextView) findViewById(R.id.generation);
        entry_count = (TextView) findViewById(R.id.entry_count);
        point = (TextView) findViewById(R.id.point);
        last_active_value = (TextView) findViewById(R.id.last_active_value);
        registered_value = (TextView) findViewById(R.id.registered_value);
        //
        username.setTypeface(font,Typeface.BOLD);
        auth.setTypeface(font);
        message.setTypeface(font,Typeface.ITALIC);
        generation.setTypeface(font);
        entry_count.setTypeface(font);
        point.setTypeface(font);
        last_active_value.setTypeface(font,Typeface.ITALIC);
        registered_value.setTypeface(font,Typeface.ITALIC);
        //
        TextView last_five_entry_title = (TextView) findViewById(R.id.last_five_entry_title);
        TextView last_active_title = (TextView) findViewById(R.id.last_active_title);
        TextView registered_title = (TextView) findViewById(R.id.registered_title);
        last_five_entry_title.setTypeface(font);
        last_active_title.setTypeface(font);
        registered_title.setTypeface(font);
        //
        userUrl = getIntent().getExtras().getString(Constants.username_parameter);
        fetchUser(userUrl);
    }

    public void fetchUser(final String strUsername) {
        setSupportProgressBarIndeterminateVisibility(Boolean.TRUE);
        userResource.getUserByUsername(strUsername, new Callback() {
            @Override
            public void failure(RetrofitError arg0) {
                setSupportProgressBarIndeterminateVisibility(Boolean.FALSE);
                //
                Utils.showMessage(getString(R.string.error), arg0.getMessage(), ProfileActivity.this);
            }

            @Override
            public void success(Object arg0, Response arg1) {
                setSupportProgressBarIndeterminateVisibility(Boolean.FALSE);
                //
                final UserResult userResult = (UserResult) arg0;
                final User user = userResult.getData();
                //
                username.setText(user.getUsername());
                auth.setText(Utils.authToString(user.getAuth(), ProfileActivity.this));
                message.setText(user.getMessage());
                generation.setText(user.getGeneration()+" . "+getString(R.string.generation));
                entry_count.setText(user.getEntry_count()+" "+getString(R.string.entry));
                point.setText(user.getPoint()+" "+getString(R.string.point));
                last_active_value.setText(Utils.dateToPrettyFormat(user.getLastLogin(),ProfileActivity.this));
                registered_value.setText(Utils.dateToPrettyFormat(user.getRegisteredAt(),ProfileActivity.this));
                //
                LastFiveEntryListAdapter lastFiveEntryListAdapter = new LastFiveEntryListAdapter(ProfileActivity.this, userResult.getEntries());
                entryListView.setAdapter(lastFiveEntryListAdapter);
                entryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Context context = view.getContext();
                        Activity activity = (Activity) context;
                        activity.finish();
                        //
                        Intent intent = new Intent(ProfileActivity.this, EntryListActivity.class);
                        intent.putExtra(Constants.entry_id_parameter, userResult.getEntries().get(i).get_id());
                        context.startActivity(intent);
                    }
                });
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
