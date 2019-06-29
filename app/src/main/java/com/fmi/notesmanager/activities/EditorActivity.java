package com.fmi.notesmanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.fmi.notesmanager.R;
import com.fmi.notesmanager.interaction.Callback;
import com.fmi.notesmanager.interaction.ServerRequest;
import com.fmi.notesmanager.interaction.UserLocalStore;
import com.fmi.notesmanager.model.Note;
import com.fmi.notesmanager.model.User;

import org.springframework.web.client.RestTemplate;

/**
 * The activity for creating, editing and deleting notes. Overrides {@link AppCompatActivity#onCreate(Bundle)},
 * {@link AppCompatActivity#onCreateOptionsMenu(Menu)} and {@link AppCompatActivity#onOptionsItemSelected(MenuItem)}.
 *
 * @author angel.beshirov
 */
public class EditorActivity extends AppCompatActivity {

    public static final String NEW_NOTE = "New note";

    private final RestTemplate restTemplate = new RestTemplate();

    private EditText title;
    private EditText content;
    private User user;
    private long id;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.etTitle);
        content = findViewById(R.id.etEditor);

        final UserLocalStore userLocalStore = new UserLocalStore(this);
        user = userLocalStore.getLoggedInUser();

        final Intent intent = getIntent();
        id = intent.getLongExtra("id", -1L);

        if (id != -1) {
            final ServerRequest serverRequest = new ServerRequest(restTemplate);
            serverRequest.retrieveSpecificNote(id, new Callback<Note>() {
                @Override
                public void done(final Note retrievedNote) {
                    if (retrievedNote != null) {
                        title.setText(retrievedNote.getTitle());
                        content.setText(retrievedNote.getContent());
                    }
                }
            });
        } else {
            title.setText(NEW_NOTE);
        }
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        final MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.app_bar_menu_editor, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
        final ServerRequest serverRequest = new ServerRequest(restTemplate);
        switch (item.getItemId()) {
            case R.id.action_save:
                if (id != -1) {
                    final Note updatedNote = new Note(id, title.getText().toString(), content.getText().toString());
                    serverRequest.updateNoteInBackground(updatedNote, new Callback() {
                        @Override
                        public void done(final Object result) {
                            startActivity(new Intent(EditorActivity.this, MainActivity.class));
                        }
                    });
                } else {
                    final Note newNote = new Note(null, title.getText().toString(), content.getText().toString());
                    serverRequest.createNoteInBackground(user, newNote, new Callback() {
                        @Override
                        public void done(final Object result) {
                            startActivity(new Intent(EditorActivity.this, MainActivity.class));
                        }
                    });
                }
                break;
            case R.id.action_delete:
                if(id != -1) {
                    serverRequest.deleteNoteInBackground(id, new Callback() {
                        @Override
                        public void done(final Object result) {
                            startActivity(new Intent(EditorActivity.this, MainActivity.class));
                        }
                    });
                }
        }

        return super.onOptionsItemSelected(item);
    }
}
