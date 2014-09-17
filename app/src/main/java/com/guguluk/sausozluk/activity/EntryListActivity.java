package com.guguluk.sausozluk.activity;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.guguluk.sausozluk.R;
import com.guguluk.sausozluk.adapter.EntryListAdapter;
import com.guguluk.sausozluk.dto.Entry;
import com.guguluk.sausozluk.dto.EntryListResult;
import com.guguluk.sausozluk.dto.EntryResult;
import com.guguluk.sausozluk.dto.Stats;
import com.guguluk.sausozluk.dto.StatsResult;
import com.guguluk.sausozluk.resource.EntryResource;
import com.guguluk.sausozluk.util.Constants;
import com.guguluk.sausozluk.util.OnSwipeTouchListener;
import com.guguluk.sausozluk.util.Utils;
import java.util.ArrayList;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class EntryListActivity extends ActionBarActivity {

    private String topicUrl;
    private Integer currentPage = 0;
    private TextView title;
    private PopupWindow popupWindow;
    private EntryResource entryResource = new EntryResource();
    private ListView entryListView;
    private Typeface font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_entry_list);
        //
        font = Utils.getFont(this);
        //
        if(findViewById(R.id.action_bar_title)!=null) {
            title = (TextView)findViewById(R.id.action_bar_title);
        } else {
            title = (TextView)findViewById(Utils.getVersionBasedTitleId(this));
        }
        title.setTypeface(font);
        title.setTextSize(Constants.title_font_size);
        //
        entryListView = (ListView) findViewById(R.id.listEntry);
        //
        topicUrl = getIntent().getExtras().getString("topicUrl");
        String entryId = getIntent().getExtras().getString("entryId");
        if(topicUrl!=null && entryId==null) {
            fetchEntries(currentPage);
        } else if(topicUrl==null && entryId!=null) {
            fetchEntry(entryId);
        } else {
            finish();
        }
    }

    public void initiatePopupWindow(String favori, String iyi_bu, String yapma) {
        LayoutInflater inflater = (LayoutInflater) EntryListActivity.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.stats_popup,
                (ViewGroup) findViewById(R.id.popup_stats));

        TextView txtFavori = (TextView)layout.findViewById(R.id.favori);
        TextView txtIyiBu = (TextView)layout.findViewById(R.id.iyi_bu);
        TextView txtYapma = (TextView)layout.findViewById(R.id.yapma);

        txtFavori.setTypeface(font);
        txtIyiBu.setTypeface(font);
        txtYapma.setTypeface(font);

        txtFavori.setText(favori);
        txtIyiBu.setText(iyi_bu);
        txtYapma.setText(yapma);

        popupWindow = new PopupWindow(layout, 400, 400, true);
        popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }

    public void fetchStats(String id) {
        setSupportProgressBarIndeterminateVisibility(Boolean.TRUE);
        entryResource.getEntryStatsById(id, new Callback() {
            @Override
            public void failure(RetrofitError arg0) {
                setSupportProgressBarIndeterminateVisibility(Boolean.FALSE);
                //
                Utils.showMessage(getString(R.string.error), arg0.getMessage(), EntryListActivity.this);
            }

            @Override
            public void success(Object arg0, Response arg1) {
                setSupportProgressBarIndeterminateVisibility(Boolean.FALSE);
                //
                final StatsResult statsResult = (StatsResult) arg0;
                final Stats stats = statsResult.getData();
                initiatePopupWindow(stats.getFavCount(),stats.getGoodCount(),stats.getBadCount());
            }
        });

    }

    private void fetchEntry(String id) {
        setSupportProgressBarIndeterminateVisibility(Boolean.TRUE);
        entryResource.getEntryById(id, new Callback() {
            @Override
            public void failure(RetrofitError arg0) {
                setSupportProgressBarIndeterminateVisibility(Boolean.FALSE);
                //
                Utils.showMessage(getString(R.string.error), arg0.getMessage(), EntryListActivity.this);
            }

            @Override
            public void success(Object arg0, Response arg1) {
                setSupportProgressBarIndeterminateVisibility(Boolean.FALSE);
                //
                final EntryResult entryResult = (EntryResult) arg0;
                final Entry entry = entryResult.getData();
                //
                title.setText(Utils.lowerCase(entry.getTopic().getName()));
                //
                List<Entry> entryList = new ArrayList<Entry>();
                entryList.add(entry);
                render(entryList);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    private void render(List<Entry> entries) {
        final List<Entry> entryList = entries;
        //
        EntryListAdapter entryListAdapter = new EntryListAdapter(EntryListActivity.this, entryList, currentPage);
        entryListView.setAdapter(entryListAdapter);
        entryListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Entry targetEntry = entryList.get(arg2);
                //
                String topic = targetEntry.getTopic().getName();
                String url = Constants.entry_base_url + targetEntry.get_id();
                String content = Utils.lowerCase(topic) + ": " + url + " " + Constants.twitter;
                //
                Utils.share(content,EntryListActivity.this);
                //
                return true;
            }
        });
    }

    private void fetchEntries(final Integer page) {
        setSupportProgressBarIndeterminateVisibility(Boolean.TRUE);
        entryResource.getTopicEntriesByPage(topicUrl, page, new Callback() {
            @Override
            public void failure(RetrofitError arg0) {
                setSupportProgressBarIndeterminateVisibility(Boolean.FALSE);
                //
                Utils.showMessage(getString(R.string.error), arg0.getMessage(), EntryListActivity.this);
            }

            @Override
            public void success(Object arg0, Response arg1) {
                setSupportProgressBarIndeterminateVisibility(Boolean.FALSE);
                //
                final EntryListResult entryListResult = (EntryListResult) arg0;
                if (entryListResult.getStatus().equalsIgnoreCase("error") || entryListResult.getEntryData().getEntry() == null || entryListResult.getEntryData().getEntry().size() == 0) {
                    return;
                } else {
                    currentPage = page;
                    Integer tempPage = currentPage + 1;
                    title.setText(Utils.lowerCase(entryListResult.getEntryData().getTopic().getName()) + " / " + tempPage);
                }
                //
                final List<Entry> entryList = entryListResult.getEntryData().getEntry();
                //
                entryListView.setOnTouchListener(new OnSwipeTouchListener(EntryListActivity.this) {
                    public void onSwipeLeft() {
                        fetchEntries(currentPage+1);
                    }
                    public void onSwipeRight() {
                        fetchEntries(currentPage-1);
                    }
                });
                //
                render(entryList);
            }
        });
    }


}
