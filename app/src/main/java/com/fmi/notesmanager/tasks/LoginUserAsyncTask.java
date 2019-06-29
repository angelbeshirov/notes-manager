package com.fmi.notesmanager.tasks;

import android.os.AsyncTask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fmi.notesmanager.model.User;
import com.fmi.notesmanager.interaction.Callback;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * Async task for logging in user into the application.
 *
 * @author angel.beshirov
 */
public class LoginUserAsyncTask extends AsyncTask<Void, Void, User> {

    private static final String SERVER_ADDRESS = "http://192.168.0.101/api.php/login";

    private User user;
    private Callback<User> userCallback;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public LoginUserAsyncTask(User user, ObjectMapper objectMapper, Callback<User> userCallback, RestTemplate restTemplate) {
        this.user = user;
        this.objectMapper = objectMapper;
        this.userCallback = userCallback;
        this.restTemplate = restTemplate;
    }

    @Override
    protected User doInBackground(Void... voids) {
        HttpEntity<String> request;
        User returnedUser = null;
        try {
            request = new HttpEntity<>(objectMapper.writeValueAsString(user));
            ResponseEntity<String> response = restTemplate.postForEntity(SERVER_ADDRESS, request, String.class);
            if (response != null && response.getBody() != null) {
                returnedUser = objectMapper.readValue(response.getBody(), User.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return returnedUser;
    }

    @Override
    protected void onPostExecute(User user) {
        super.onPostExecute(user);
        userCallback.done(user);
    }

}
