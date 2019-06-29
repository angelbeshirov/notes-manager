package com.fmi.notesmanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.fmi.notesmanager.R;
import com.fmi.notesmanager.interaction.Callback;
import com.fmi.notesmanager.interaction.NotesAdapter;
import com.fmi.notesmanager.interaction.ServerRequest;
import com.fmi.notesmanager.interaction.UserLocalStore;
import com.fmi.notesmanager.model.Note;
import com.fmi.notesmanager.model.User;

import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * This is the main activity which the user sees after log in. Overrides {@link AppCompatActivity#onCreate(Bundle)},
 * {@link AppCompatActivity#onStart()}, {@link AppCompatActivity#onCreateOptionsMenu(Menu)}
 * and {@link AppCompatActivity#onOptionsItemSelected(MenuItem)}.
 *
 * @author angel.beshirov
 */
public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_EDIT = 1;
    private final RestTemplate restTemplate = new RestTemplate();

    private ListView listView;
    private UserLocalStore userLocalStore;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        userLocalStore = new UserLocalStore(this);

        setSupportActionBar(toolbar);

        listView = findViewById(R.id.notesList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                final Note note = (Note) parent.getItemAtPosition(position);
                final Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                intent.putExtra("id", note.getId());
                startActivityForResult(intent, REQUEST_CODE_EDIT);
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        if (authenticate()) {
            displayUserNotes();
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        final MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.app_bar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                userLocalStore.clear();
                userLocalStore.setUserLoggedIn(false);
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.action_create:
                startActivity(new Intent(this, EditorActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean authenticate() {
        return userLocalStore.isUserLoggedIn();
    }

    private void displayUserNotes() {
        final ServerRequest serverRequest = new ServerRequest(restTemplate);
        final User user = userLocalStore.getLoggedInUser();

        serverRequest.retrieveAllNotesForUser(user, new Callback<List<Note>>() {
            @Override
            public void done(final List<Note> notes) {
                final NotesAdapter notesAdapter = new NotesAdapter(MainActivity.this, android.R.layout.activity_list_item, notes);
                listView.setAdapter(notesAdapter);
            }
        });
    }
}
