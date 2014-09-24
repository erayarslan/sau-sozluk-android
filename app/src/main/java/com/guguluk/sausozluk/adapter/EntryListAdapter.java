package com.guguluk.sausozluk.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.guguluk.sausozluk.R;
import com.guguluk.sausozluk.activity.EntryListActivity;
import com.guguluk.sausozluk.activity.ProfileActivity;
import com.guguluk.sausozluk.dto.Entry;
import com.guguluk.sausozluk.util.Constants;
import com.guguluk.sausozluk.util.Utils;
import java.util.List;

@SuppressLint({"ViewHolder", "InflateParams"})
public class EntryListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Entry> entryList;
    private Context context;
    private Integer page;
    private String token;

    public EntryListAdapter(String token, Activity activity, List<Entry> entryList, Integer page) {
        this.mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.entryList = entryList;
        this.context = activity.getBaseContext();
        this.page = page;
        this.token = token;
    }

    @Override
    public int getCount() {
        return entryList.size();
    }

    @Override
    public Entry getItem(int position) {
        return entryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = mInflater.inflate(R.layout.row_entry_list, null);
        //
        TextView textEntryNumber = (TextView) rowView.findViewById(R.id.entryNumber);
        TextView textEntryContent = (TextView) rowView.findViewById(R.id.entryContent);
        TextView textEntryDetail = (TextView) rowView.findViewById(R.id.entryDetail);
        Button buttonEntryStats = (Button) rowView.findViewById(R.id.entryStats);
        //
        Typeface font = Utils.getFont(context);
        textEntryNumber.setTypeface(font);
        textEntryContent.setTypeface(font);
        textEntryDetail.setTypeface(font);
        buttonEntryStats.setTypeface(font);
        //
        if(token != null && !token.trim().equalsIgnoreCase(Constants.empty)) {
            buttonEntryStats.setVisibility(View.VISIBLE);
        } else {
            buttonEntryStats.setVisibility(View.INVISIBLE);
        }
        //
        final Entry entry = entryList.get(position);
        //
        textEntryNumber.setText(Constants.sharp + ((page*Constants.pageEntryCount)+position+1));
        textEntryContent.setText(Utils.prettyBuildForContent(entry.getText()));
        buttonEntryStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EntryListActivity entryListActivity = (EntryListActivity) view.getContext();
                entryListActivity.fetchStats(entry.get_id());
            }
        });
        textEntryDetail.setText(
                Utils.lowerCase(entry.getCreatedBy().getUsername()) + " / " +
                        Utils.dateToPrettyFormat(entry.getCreatedAt(), context));
        textEntryDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = (Activity) view.getContext();
                activity.finish();
                //
                Intent intent = new Intent(view.getContext(), ProfileActivity.class);
                intent.putExtra(Constants.username_parameter, entry.getCreatedBy().getClean());
                view.getContext().startActivity(intent);
            }
        });
        //
        textEntryContent.setMovementMethod(LinkMovementMethod.getInstance());
        //
        return rowView;
    }
}