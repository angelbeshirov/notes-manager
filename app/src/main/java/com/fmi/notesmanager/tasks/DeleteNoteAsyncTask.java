package com.fmi.notesmanager.tasks;

import android.os.AsyncTask;

import com.fmi.notesmanager.interaction.Callback;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Async task for deleting task from the server.
 *
 * @author angel.beshirov
 */
public class DeleteNoteAsyncTask extends AsyncTask<Void, Void, Void> {
    private static final String SERVER_ADDRESS = "http://192.168.0.101/api.php/delete_note";

    private long id;
    private Callback<?> userCallback;
    private final RestTemplate restTemplate;

    public DeleteNoteAsyncTask(long id, Callback<?> userCallback, RestTemplate restTemplate) {
        this.userCallback = userCallback;
        this.restTemplate = restTemplate;
        this.id = id;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(SERVER_ADDRESS)
                .queryParam("id", id);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.DELETE,
                entity,
                String.class);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        userCallback.done(null);
        super.onPostExecute(aVoid);
    }
}
