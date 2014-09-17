package com.guguluk.sausozluk.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.style.URLSpan;
import android.view.View;

import com.guguluk.sausozluk.activity.EntryListActivity;

public class ClickableBkzSpan extends URLSpan {
    public ClickableBkzSpan(String url) {
        super(url);
    }

    @Override
    public void onClick(@NonNull View widget) {
        Context context = widget.getContext();
        Activity activity = (Activity) context;
        activity.finish();
        Intent intent = new Intent(context, EntryListActivity.class);
        intent.putExtra("topicUrl", Utils.dirtyUrl(getURL()));
        context.startActivity(intent);
    }
}
