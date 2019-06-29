package com.fmi.notesmanager.tasks;

import android.os.AsyncTask;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fmi.notesmanager.model.Note;
import com.fmi.notesmanager.model.User;
import com.fmi.notesmanager.interaction.Callback;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Async task for retrieving all notes for a particular user from the server.
 *
 * @author angel.beshirov
 */
public class RetrieveAllNotesAsyncTask extends AsyncTask<Void, Void, List<Note>> {
    private static final String SERVER_ADDRESS = "http://192.168.0.101/api.php/get_all_notes";

    private final User user;
    private final Callback<List<Note>> userCallback;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public RetrieveAllNotesAsyncTask(final User user, final ObjectMapper objectMapper, final Callback<List<Note>> userCallback, final RestTemplate restTemplate) {
        this.user = user;
        this.objectMapper = objectMapper;
        this.userCallback = userCallback;
        this.restTemplate = restTemplate;
    }

    @Override
    protected List<Note> doInBackground(final Void... voids) {
        List<Note> result = new ArrayList<>();
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(SERVER_ADDRESS)
                .queryParam("id", user.getId());

        final HttpEntity<?> entity = new HttpEntity<>(headers);

        final HttpEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class);

        if (response != null && response.getBody() != null) {
            try {
                result = objectMapper.readValue(response.getBody(), new TypeReference<List<Note>>() {
                });
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    @Override
    protected void onPostExecute(final List<Note> notes) {
        super.onPostExecute(notes);
        userCallback.done(notes);
    }
}
