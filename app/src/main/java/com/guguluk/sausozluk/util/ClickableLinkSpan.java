package com.guguluk.sausozluk.util;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.style.URLSpan;
import android.view.View;

public class ClickableLinkSpan extends URLSpan {
    public ClickableLinkSpan(String url) {
        super(url);
    }

    @Override
    public void onClick(@NonNull View widget) {
        Utils.visitUrl(getURL(),(Activity) widget.getContext());
    }
}
