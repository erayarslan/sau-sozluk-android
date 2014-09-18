package com.guguluk.sausozluk.resource;

import com.guguluk.sausozluk.service.UserService;
import com.guguluk.sausozluk.util.SauSozlukRestAdapter;

import retrofit.Callback;

@SuppressWarnings("unchecked")
public class UserResource {
    private UserService userService;

    public UserResource() {
        userService = new SauSozlukRestAdapter().getRestAdapter().create(UserService.class);
    }

    public void getUserByUsername(String username, Callback callback) {
        userService.getUserByUsername(username, callback);
    }
}
