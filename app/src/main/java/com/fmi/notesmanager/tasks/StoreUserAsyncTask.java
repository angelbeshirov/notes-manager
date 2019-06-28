package com.fmi.notesmanager.tasks;

import android.os.AsyncTask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fmi.notesmanager.model.User;
import com.fmi.notesmanager.model.UserCallback;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class StoreUserAsyncTask extends AsyncTask<Void, Void, Void> {
    public static final String SERVER_ADDRESS = "http://192.168.0.101/api.php/register";

    private final User user;
    private final UserCallback userCallback;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public StoreUserAsyncTask(User user, UserCallback userCallback, RestTemplate restTemplate) {
        this.user = user;
        this.userCallback = userCallback;
        this.restTemplate = restTemplate;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        HttpEntity<String> request = null;
        try {
            request = new HttpEntity<>(objectMapper.writeValueAsString(user));
            restTemplate.postForObject(SERVER_ADDRESS, request, String.class);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        userCallback.done(null);
        super.onPostExecute(aVoid);
    }
}