package com.fmi.notesmanager.model;

import com.fmi.notesmanager.tasks.LoginUserAsyncTask;
import com.fmi.notesmanager.tasks.StoreUserAsyncTask;

import org.springframework.web.client.RestTemplate;


public class ServerRequest {

    RestTemplate restTemplate = new RestTemplate();

    public ServerRequest(RestTemplate restTemplate) {

    }

    public void storeUserDataInBackground(final User user, final UserCallback userCallback) {
        new StoreUserAsyncTask(user, userCallback, restTemplate).execute();
    }

    public void fetchUserDataInBackground(final User user, final UserCallback userCallback) {
        new LoginUserAsyncTask(user, userCallback, restTemplate).execute();
    }

}
