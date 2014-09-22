package com.guguluk.sausozluk.span;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.style.URLSpan;
import android.view.View;

import com.guguluk.sausozluk.util.Utils;

public class ClickableLinkSpan extends URLSpan {
    public ClickableLinkSpan(String url) {
        super(url);
    }

    @Override
    public void onClick(@NonNull View widget) {
        Utils.visitUrl(getURL(), (Activity) widget.getContext());
    }
}
