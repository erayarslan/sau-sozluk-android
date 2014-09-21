package com.guguluk.sausozluk.activity;

import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Window;
import android.widget.TextView;

import com.guguluk.sausozluk.R;
import com.guguluk.sausozluk.resource.GitHubContentResource;
import com.guguluk.sausozluk.util.Constants;
import com.guguluk.sausozluk.util.Utils;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class AboutActivity extends ActionBarActivity {

    GitHubContentResource gitHubContentResource = new GitHubContentResource();
    TextView aboutContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_about);
        //
        Typeface font = Utils.getFont(this);
        //
        Utils.invisibleIcon(this);
        //
        TextView title;
        if(findViewById(R.id.action_bar_title)!=null) {
            title = (TextView)findViewById(R.id.action_bar_title);
        } else {
            title = (TextView)findViewById(Utils.getVersionBasedTitleId(this));
        }
        title.setTypeface(font);
        title.setTextSize(Constants.title_font_size);
        //
        aboutContent = (TextView)findViewById(R.id.aboutContent);
        aboutContent.setTypeface(font);
        fetchContent();
    }

    private void fetchContent() {
        setSupportProgressBarIndeterminateVisibility(Boolean.TRUE);
        gitHubContentResource.getProjectReadMe(new Callback() {
            @Override
            public void failure(RetrofitError arg0) {
                setSupportProgressBarIndeterminateVisibility(Boolean.FALSE);
                //
                Utils.showMessage(getString(R.string.error), arg0.getMessage(), AboutActivity.this);
            }

            @Override
            public void success(Object arg0, Response arg1) {
                setSupportProgressBarIndeterminateVisibility(Boolean.FALSE);
                //
                final String result = (String) arg0;
                //
                aboutContent.setText(Html.fromHtml(result));
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
