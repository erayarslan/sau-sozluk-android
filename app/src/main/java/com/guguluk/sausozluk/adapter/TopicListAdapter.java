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
import com.guguluk.sausozluk.dto.Topic;
import com.guguluk.sausozluk.util.Utils;
import java.util.List;

@SuppressLint({"ViewHolder", "InflateParams"})
public class TopicListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Topic> topicList;
    private Context context;

    public TopicListAdapter(Activity activity, List<Topic> topicList) {
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.topicList = topicList;
        this.context = activity.getBaseContext();
    }

    @Override
    public int getCount() {
        return topicList.size();
    }

    @Override
    public Topic getItem(int position) {
        return topicList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = mInflater.inflate(R.layout.row_topic_list, null);
        //
        TextView textTopicName = (TextView) rowView.findViewById(R.id.topicName);
        TextView textEntryCount = (TextView) rowView.findViewById(R.id.topicEntryCount);
        //
        Topic topic = topicList.get(position);
        //
        Typeface font = Utils.getFont(context);
        textTopicName.setText(Utils.lowerCase(topic.getName()));
        textTopicName.setTypeface(font);
        if(topic.getEntry_count().intValue()!=0) {
            textEntryCount.setText(topic.getEntry_count().toString());
            textEntryCount.setTypeface(font);
            textEntryCount.setBackgroundResource(R.drawable.entry_count_shape);
        } else {
            textEntryCount.setVisibility(View.GONE);
        }
        //
        return rowView;
    }
}
