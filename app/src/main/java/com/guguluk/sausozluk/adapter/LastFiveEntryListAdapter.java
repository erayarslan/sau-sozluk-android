package com.guguluk.sausozluk.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.guguluk.sausozluk.R;
import com.guguluk.sausozluk.dto.Entries;
import com.guguluk.sausozluk.util.Utils;

import java.util.List;

@SuppressLint({"ViewHolder", "InflateParams"})
public class LastFiveEntryListAdapter extends BaseAdapter{
    private LayoutInflater mInflater;
    private List<Entries> entryList;
    private Context context;

    public LastFiveEntryListAdapter(Activity activity, List<Entries> entryList) {
        this.mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.entryList = entryList;
        this.context = activity.getBaseContext();
    }

    @Override
    public int getCount() {
        return entryList.size();
    }

    @Override
    public Entries getItem(int position) {
        return entryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = mInflater.inflate(R.layout.row_last_five_entry_list, null);
        //
        Entries entries = entryList.get(position);
        //
        Typeface font = Utils.getFont(context);
        TextView txtEntry = (TextView) rowView.findViewById(R.id.entry);
        txtEntry.setTypeface(font,Typeface.ITALIC);
        txtEntry.setText(entries.getTopic().getName()+"/@"+entries.getCreatedBy().getUsername());
        //
        return rowView;
    }
}
