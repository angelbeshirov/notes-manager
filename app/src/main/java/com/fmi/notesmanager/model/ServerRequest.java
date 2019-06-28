package com.fmi.notesmanager.model;

import com.fmi.notesmanager.tasks.LoginUserAsyncTask;
import com.fmi.notesmanager.tasks.RetrieveAllNotesAsyncTask;
import com.fmi.notesmanager.tasks.StoreUserAsyncTask;

import org.springframework.web.client.RestTemplate;

import java.util.List;


public class ServerRequest {

    private final RestTemplate restTemplate = new RestTemplate();

    public ServerRequest(RestTemplate restTemplate) {

    }

    public void storeUserDataInBackground(final User user, final UserCallback<User> userCallback) {
        new StoreUserAsyncTask(user, userCallback, restTemplate).execute();
    }

    public void fetchUserDataInBackground(final User user, final UserCallback<User> userCallback) {
        new LoginUserAsyncTask(user, userCallback, restTemplate).execute();
    }

    public void retrieveAllNotesForUser(final User user, final UserCallback<List<Note>> userCallback) {
        new RetrieveAllNotesAsyncTask(user, userCallback, restTemplate).execute();
    }

}
