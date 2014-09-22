package com.guguluk.sausozluk.span;

import android.support.annotation.NonNull;
import android.text.style.URLSpan;
import android.view.View;

import com.guguluk.sausozluk.util.Constants;
import com.guguluk.sausozluk.util.Utils;

public class ClickableYildizliBkzSpan extends URLSpan {
    public ClickableYildizliBkzSpan(String url) {
        super(url);
    }

    @Override
    public void onClick(@NonNull View widget) {
        Utils.showMessage(Constants.star.trim(), Utils.lowerCase(getURL()), widget.getContext());
    }
}
