package com.guguluk.sausozluk.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.guguluk.sausozluk.R;
import com.guguluk.sausozluk.dto.LoginResponse;
import com.guguluk.sausozluk.resource.LoginResource;
import com.guguluk.sausozluk.util.Constants;
import com.guguluk.sausozluk.util.ObscuredSharedPreferences;
import com.guguluk.sausozluk.util.Utils;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class LoginActivity extends ActionBarActivity {

    LoginResource loginResource = new LoginResource();
    //
    SharedPreferences prefs;
    //
    TextView loginFormTitle;
    TextView loginUsernameTitle;
    EditText loginUsernameInput;
    TextView loginPasswordTitle;
    EditText loginPasswordInput;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_login);
        //
        prefs = new ObscuredSharedPreferences(this, this.getSharedPreferences(Constants.store_info_name, Context.MODE_PRIVATE));
        //
        Typeface font = Utils.getFont(this);
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
        loginFormTitle = (TextView)findViewById(R.id.loginFormTitle);
        loginUsernameTitle = (TextView)findViewById(R.id.loginUsernameTitle);
        loginUsernameInput = (EditText)findViewById(R.id.loginUsernameInput);
        loginPasswordTitle = (TextView)findViewById(R.id.loginPasswordTitle);
        loginPasswordInput = (EditText)findViewById(R.id.loginPasswordInput);
        loginButton = (Button)findViewById(R.id.loginButton);
        //
        loginFormTitle.setTypeface(font);
        loginUsernameTitle.setTypeface(font);
        loginUsernameInput.setTypeface(font);
        loginPasswordTitle.setTypeface(font);
        loginPasswordInput.setTypeface(font);
        loginButton.setTypeface(font);
        //
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doLogin(
                        loginUsernameInput.getText().toString(),
                        loginPasswordInput.getText().toString()
                );
            }
        });
    }

    private void doLogin(String username, String password) {
        setSupportProgressBarIndeterminateVisibility(Boolean.TRUE);
        loginResource.login(username, password, new Callback() {
            @Override
            public void failure(RetrofitError arg0) {
                setSupportProgressBarIndeterminateVisibility(Boolean.FALSE);
                //
                Utils.showMessage(getString(R.string.error), arg0.getMessage(), LoginActivity.this);
            }

            @Override
            public void success(Object arg0, Response arg1) {
                setSupportProgressBarIndeterminateVisibility(Boolean.FALSE);
                //
                final LoginResponse loginResponse = (LoginResponse) arg0;
                //
                if(loginResponse.getStatus().equalsIgnoreCase(Constants.ok)) {
                    prefs.edit().putString(Constants.store_token,loginResponse.getToken()).commit();
                    prefs.edit().putString(Constants.store_username,loginResponse.getData().getUsername()).commit();
                    prefs.edit().putString(Constants.store_clean,loginResponse.getData().getClean()).commit();
                    prefs.edit().putString(Constants.store_auth,loginResponse.getData().getAuth().toString()).commit();
                    prefs.edit().putString(Constants.store_id,loginResponse.getData().get_id()).commit();
                    //
                    Toast.makeText(LoginActivity.this, "w0w!", Toast.LENGTH_LONG).show();
                    //
                    finish();
                    Intent intent = new Intent(LoginActivity.this, TopicListActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, loginResponse.getDesc(), Toast.LENGTH_SHORT).show();
                    loginUsernameInput.setText(Constants.empty);
                    loginPasswordInput.setText(Constants.empty);
                    loginUsernameInput.setFocusable(true);
                    loginUsernameInput.requestFocus();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
