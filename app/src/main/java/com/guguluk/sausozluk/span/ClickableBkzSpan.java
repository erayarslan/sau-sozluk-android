package com.guguluk.sausozluk.span;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.style.URLSpan;
import android.view.View;

import com.guguluk.sausozluk.activity.EntryListActivity;
import com.guguluk.sausozluk.util.Utils;

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
        intent.putExtra("topicUrl", getURL());
        context.startActivity(intent);
    }
}
