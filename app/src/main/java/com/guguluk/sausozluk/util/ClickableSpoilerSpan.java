package com.guguluk.sausozluk.util;

import android.support.annotation.NonNull;
import android.text.style.URLSpan;
import android.view.View;

public class ClickableSpoilerSpan extends URLSpan {
    public ClickableSpoilerSpan(String url) {
        super(url);
    }

    @Override
    public void onClick(@NonNull View widget) {
        Utils.showMessage(Constants.spoiler.trim(), Utils.lowerCase(getURL()), widget.getContext());
    }
}
