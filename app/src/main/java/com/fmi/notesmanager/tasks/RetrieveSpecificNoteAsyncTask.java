package com.fmi.notesmanager.tasks;

import android.os.AsyncTask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fmi.notesmanager.interaction.Callback;
import com.fmi.notesmanager.model.Note;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

/**
 * Async task for retrieving specific note for user from the server.
 *
 * @author angel.beshirov
 */
public class RetrieveSpecificNoteAsyncTask extends AsyncTask<Void, Void, Note> {
    private static final String SERVER_ADDRESS = "http://192.168.0.101/api.php/get_note";

    private final long noteID;
    private final Callback<Note> userCallback;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public RetrieveSpecificNoteAsyncTask(final long noteID, final ObjectMapper objectMapper, final Callback<Note> userCallback, final RestTemplate restTemplate) {
        this.noteID = noteID;
        this.objectMapper = objectMapper;
        this.userCallback = userCallback;
        this.restTemplate = restTemplate;
    }

    @Override
    protected Note doInBackground(final Void... voids) {
        Note note = null;
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(SERVER_ADDRESS)
                .queryParam("id", noteID);

        final HttpEntity<?> entity = new HttpEntity<>(headers);

        final HttpEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class);

        if (response != null && response.getBody() != null) {
            try {
                note = objectMapper.readValue(response.getBody(), Note.class);
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }

        return note;
    }

    @Override
    protected void onPostExecute(final Note note) {
        super.onPostExecute(note);
        userCallback.done(note);
    }
}
