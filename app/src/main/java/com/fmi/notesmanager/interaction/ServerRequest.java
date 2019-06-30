package com.fmi.notesmanager.interaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fmi.notesmanager.model.Note;
import com.fmi.notesmanager.model.User;
import com.fmi.notesmanager.tasks.CreateNoteAsyncTask;
import com.fmi.notesmanager.tasks.DeleteNoteAsyncTask;
import com.fmi.notesmanager.tasks.LoginUserAsyncTask;
import com.fmi.notesmanager.tasks.RetrieveAllNotesAsyncTask;
import com.fmi.notesmanager.tasks.RetrieveSpecificNoteAsyncTask;
import com.fmi.notesmanager.tasks.StoreUserAsyncTask;
import com.fmi.notesmanager.tasks.UpdateNoteAsyncTask;

import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Wrapper of the communication with the server.
 *
 * @author angel.beshirov
 */
public class ServerRequest {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public ServerRequest(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public void storeUserDataInBackground(final User user, final Callback<Boolean> userCallback) {
        new StoreUserAsyncTask(user, objectMapper, userCallback, restTemplate).execute();
    }

    public void fetchUserDataInBackground(final User user, final Callback<User> userCallback) {
        new LoginUserAsyncTask(user, objectMapper, userCallback, restTemplate).execute();
    }

    public void retrieveAllNotesForUser(final User user, final Callback<List<Note>> userCallback) {
        new RetrieveAllNotesAsyncTask(user, objectMapper, userCallback, restTemplate).execute();
    }

    public void retrieveSpecificNote(final long noteID, final Callback<Note> userCallback) {
        new RetrieveSpecificNoteAsyncTask(noteID, objectMapper, userCallback, restTemplate).execute();
    }

    public void createNoteInBackground(final User user, final Note note, final Callback userCallback) {
        new CreateNoteAsyncTask(user, note, objectMapper, userCallback, restTemplate).execute();
    }

    public void updateNoteInBackground(final Note note, final Callback userCallback) {
        new UpdateNoteAsyncTask(note, objectMapper, userCallback, restTemplate).execute();
    }

    public void deleteNoteInBackground(final long noteID, final Callback userCallback) {
        new DeleteNoteAsyncTask(noteID, userCallback, restTemplate).execute();
    }

}
