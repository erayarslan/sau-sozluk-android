package com.guguluk.sausozluk.util;

import android.support.annotation.NonNull;
import android.text.style.URLSpan;
import android.view.View;

public class ClickableYildizliBkzSpan extends URLSpan {
    public ClickableYildizliBkzSpan(String url) {
        super(url);
    }

    @Override
    public void onClick(@NonNull View widget) {
        Utils.showMessage(Constants.star,Utils.lowerCase(getURL()), widget.getContext());
    }
}
