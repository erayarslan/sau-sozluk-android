package com.guguluk.sausozluk.resource;

import com.guguluk.sausozluk.service.LoginService;
import com.guguluk.sausozluk.util.SauSozlukRestAdapter;

import retrofit.Callback;

@SuppressWarnings("unchecked")
public class LoginResource {
    private LoginService loginService;

    public LoginResource() {
        loginService = new SauSozlukRestAdapter().getRestAdapter().create(LoginService.class);
    }

    public void login(String username, String password, Callback callback) {
        loginService.login(username, password, callback);
    }
}
