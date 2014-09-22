package com.guguluk.sausozluk.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.Selection;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.guguluk.sausozluk.R;
import com.guguluk.sausozluk.adapter.EntryListAdapter;
import com.guguluk.sausozluk.dto.CreateTopicResult;
import com.guguluk.sausozluk.dto.Entry;
import com.guguluk.sausozluk.dto.EntryListResult;
import com.guguluk.sausozluk.dto.EntryResult;
import com.guguluk.sausozluk.dto.Stats;
import com.guguluk.sausozluk.dto.StatsResult;
import com.guguluk.sausozluk.dto.Status;
import com.guguluk.sausozluk.dto.Topic;
import com.guguluk.sausozluk.resource.EntryResource;
import com.guguluk.sausozluk.resource.TopicResource;
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
    private String topicName;
    private Integer currentPage = 0;
    private TextView title;
    private PopupWindow statsPopupWindow;
    private PopupWindow newPopupWindow;
    private EntryResource entryResource = new EntryResource();
    private TopicResource topicResource = new TopicResource();
    private ListView entryListView;
    private Typeface font;
    //
    private Topic topic;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_entry_list);
        //
        font = Utils.getFont(this);
        //
        Utils.invisibleIcon(this);
        //
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
        entryListView = (ListView) findViewById(R.id.listEntry);
        //
        topicName = getIntent().getExtras().getString("topicUrl");
        topicUrl = Utils.dirtyUrl(getIntent().getExtras().getString("topicUrl"));
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

        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, r.getDisplayMetrics());

        statsPopupWindow = new PopupWindow(layout, (int)px, (int)px, true);
        statsPopupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                statsPopupWindow.dismiss();
                return true;
            }
        });
    }

    public void initiateNewPopupWindow() {
        LayoutInflater inflater = (LayoutInflater) EntryListActivity.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.new_popup,
                (ViewGroup) findViewById(R.id.popup_new));
        //
        final EditText entry_content = (EditText)layout.findViewById(R.id.entry_content);
        //
        if(topic!=null) {
            entry_content.setHint(topic.getName() + " " + entry_content.getHint());
        } else {
            entry_content.setHint(getString(R.string.new_topic_hint));
        }
        //
        Button bkz = (Button)layout.findViewById(R.id.bkz);
        Button spoiler = (Button)layout.findViewById(R.id.spoiler);
        Button link = (Button)layout.findViewById(R.id.link);
        Button yildiz = (Button)layout.findViewById(R.id.yildiz);
        Button yazar = (Button)layout.findViewById(R.id.yazar);
        Button atesle = (Button)layout.findViewById(R.id.atesle);
        Button x = (Button)layout.findViewById(R.id.x);
        entry_content.setTypeface(font);
        bkz.setTypeface(font);
        spoiler.setTypeface(font);
        link.setTypeface(font);
        yildiz.setTypeface(font);
        yazar.setTypeface(font);
        atesle.setTypeface(font);
        x.setTypeface(font);
        bkz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.showInput("hangi başlığa bkz verilecek?", "bkz", EntryListActivity.this, entry_content);
            }
        });
        spoiler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.showInput("hangi içerik spoiler olacak?","spoiler", EntryListActivity.this, entry_content);
            }
        });
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.showInput("hangi linki ekleyeceksiniz?","link", EntryListActivity.this, entry_content);
            }
        });
        yildiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.showInput("hangi başlığa yıldızlı bkz verilecek?","*", EntryListActivity.this, entry_content);
            }
        });
        yazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.showInput("hangi yazara yönlendirilecek?","yazar", EntryListActivity.this, entry_content);
            }
        });
        atesle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(entry_content.getText().toString().trim().equalsIgnoreCase(Constants.empty)) {
                    Toast.makeText(EntryListActivity.this, getString(R.string.empty_entry), Toast.LENGTH_SHORT).show();
                    return;
                }
                if(topic!=null) {
                    createEntry(token, entry_content.getText().toString(), Utils.getUserId(EntryListActivity.this), topic.get_id());
                } else {
                    createTopic(token, topicName, topicUrl, Utils.getUserId(EntryListActivity.this), entry_content.getText().toString());
                }
            }
        });
        x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newPopupWindow.dismiss();
            }
        });
        //
        if(newPopupWindow == null) {
            newPopupWindow = new PopupWindow(layout, (int)Utils.dpTopx(300,this), (int)Utils.dpTopx(250,this), true);
        }
        newPopupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
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
                //
                topic = entryResult.getData().getTopic();
                //
                render(entryList);
            }
        });
    }

    private void createTopic(String stoken, String name, String url, String createdBy, final String content) {
        setSupportProgressBarIndeterminateVisibility(Boolean.TRUE);
        topicResource.createTopic(stoken, name, url, createdBy, new Callback() {
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
                final CreateTopicResult createTopicResult = (CreateTopicResult) arg0;
                //
                createEntry(token, content, Utils.getUserId(EntryListActivity.this), createTopicResult.getData().get_id());
            }
        });
    }

    private void createEntry(String stoken, String text, String createdBy, String topic) {
        setSupportProgressBarIndeterminateVisibility(Boolean.TRUE);
        entryResource.createEntry(stoken, text, createdBy, topic, new Callback() {
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
                final Status status = (Status) arg0;
                //
                if(status.getStatus().equalsIgnoreCase(Constants.ok)) {
                    newPopupWindow.dismiss();
                    newPopupWindow = null;
                    fetchEntries(0);
                } else {
                    Toast.makeText(EntryListActivity.this, status.getDesc(), Toast.LENGTH_SHORT).show();
                }
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
                //
                if(entryListResult.getEntryData()!=null && entryListResult.getEntryData().getTopic() != null) {
                    topic = entryListResult.getEntryData().getTopic();
                } else {
                    return;
                }
                //
                if (entryListResult.getStatus().equalsIgnoreCase("error") || entryListResult.getEntryData()==null || entryListResult.getEntryData().getEntry() == null || entryListResult.getEntryData()==null ||  entryListResult.getEntryData().getEntry().size() == 0) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.entry, menu);
        //
        MenuItem newItem = menu.findItem(R.id.action_new);
        //
        if(token != null && !token.trim().equalsIgnoreCase(Constants.empty)) {
            newItem.setVisible(true);
        } else {
            newItem.setVisible(false);
        }
        //
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //
        if(id == R.id.action_new) {
            initiateNewPopupWindow();
        }
        //
        return super.onOptionsItemSelected(item);
    }


}
