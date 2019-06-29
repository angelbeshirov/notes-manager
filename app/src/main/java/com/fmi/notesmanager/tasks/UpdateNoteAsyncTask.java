package com.fmi.notesmanager.tasks;

import android.os.AsyncTask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fmi.notesmanager.interaction.Callback;
import com.fmi.notesmanager.model.Note;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * Async task for updating new note.
 *
 * @author angel.beshirov
 */
public class UpdateNoteAsyncTask extends AsyncTask<Void, Void, Void> {
    private static final String SERVER_ADDRESS = "http://192.168.0.101/api.php/update_note";

    private final Note note;
    private final Callback<?> userCallback;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public UpdateNoteAsyncTask(final Note note, final ObjectMapper objectMapper, final Callback<?> userCallback, final RestTemplate restTemplate) {
        this.userCallback = userCallback;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.note = note;
    }

    @Override
    protected Void doInBackground(final Void... voids) {
        final HttpEntity<String> request;
        try {
            request = new HttpEntity<>(objectMapper.writeValueAsString(note));
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
