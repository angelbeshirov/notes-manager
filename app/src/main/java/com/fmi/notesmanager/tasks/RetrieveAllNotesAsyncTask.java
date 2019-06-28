package com.fmi.notesmanager.tasks;

import android.os.AsyncTask;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fmi.notesmanager.model.Note;
import com.fmi.notesmanager.model.User;
import com.fmi.notesmanager.model.UserCallback;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RetrieveAllNotesAsyncTask extends AsyncTask<Void, Void, List<Note>> {
    public static final String SERVER_ADDRESS = "http://192.168.0.101/api.php/get_all_notes";

    private User user;
    private UserCallback<List<Note>> userCallback;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public RetrieveAllNotesAsyncTask(User user, UserCallback<List<Note>> userCallback, RestTemplate restTemplate) {
        this.user = user;
        this.userCallback = userCallback;
        this.restTemplate = restTemplate;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected List<Note> doInBackground(Void... voids) {
        List<Note> result = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(SERVER_ADDRESS)
                .queryParam("id", user.getId());

        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class);

        System.out.println(user);
        System.out.println("Sending to + " + builder.toUriString());

        if(response != null && response.getBody() != null) {
            try {
                result = objectMapper.readValue(response.getBody(), new TypeReference<List<Note>>() {});
            } catch (IOException e) {
                System.out.println("Error while parsing JSON from server");
            }
        }

        return result;
    }

    @Override
    protected void onPostExecute(List<Note> notes) {
        super.onPostExecute(notes);
        userCallback.done(notes);
    }
}
