package com.guguluk.sausozluk.util;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.style.URLSpan;
import android.view.View;

import com.guguluk.sausozluk.activity.ProfileActivity;

public class ClickableYazarSpan extends URLSpan {
    public ClickableYazarSpan(String url) {
        super(url);
    }

    @Override
    public void onClick(@NonNull View widget) {
        Activity activity = (Activity) widget.getContext();
        activity.finish();
        //
        Intent intent = new Intent(widget.getContext(), ProfileActivity.class);
        intent.putExtra(Constants.username_parameter, Utils.dirtyUrl(getURL()));
        widget.getContext().startActivity(intent);
    }
}
