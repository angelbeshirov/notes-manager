package com.fmi.notesmanager.tasks;

import android.os.AsyncTask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fmi.notesmanager.model.Note;
import com.fmi.notesmanager.model.User;
import com.fmi.notesmanager.interaction.Callback;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Async task for creating new note in the server.
 *
 * @author angel.beshirov
 */
public class CreateNoteAsyncTask extends AsyncTask<Void, Void, Void> {
    private static final String SERVER_ADDRESS = "http://192.168.0.101/api.php/create_note";

    private Note note;
    private User user;
    private Callback<?> userCallback;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public CreateNoteAsyncTask(final User user, final Note note, final ObjectMapper objectMapper, final Callback<?> userCallback, final RestTemplate restTemplate) {
        this.user = user;
        this.note = note;
        this.objectMapper = objectMapper;
        this.userCallback = userCallback;
        this.restTemplate = restTemplate;
    }

    @Override
    protected Void doInBackground(final Void... voids) {
        final HttpEntity<String> request;
        final Map<String, Object> toSend = new HashMap<>();
        toSend.put("id", user.getId());
        toSend.put("note", note);

        try {
            request = new HttpEntity<>(objectMapper.writeValueAsString(toSend));
            restTemplate.postForEntity(SERVER_ADDRESS, request, String.class);
        } catch (final IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(final Void aVoid) {
        userCallback.done(null);
        super.onPostExecute(aVoid);
    }
}
