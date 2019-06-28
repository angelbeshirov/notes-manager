package com.fmi.notesmanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cursoradapter.widget.CursorAdapter;

import com.fmi.notesmanager.R;
import com.fmi.notesmanager.model.Note;
import com.fmi.notesmanager.model.ServerRequest;
import com.fmi.notesmanager.model.User;
import com.fmi.notesmanager.model.UserCallback;
import com.fmi.notesmanager.model.UserLocalStore;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.springframework.web.client.RestTemplate;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private final RestTemplate restTemplate = new RestTemplate();


    private static final int EDITOR_REQUEST_CODE = 1001;

//    private CursorAdapter cursorAdapter;
    private Toolbar toolbar;
    private ListView listView;
    private UserLocalStore userLocalStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        userLocalStore = new UserLocalStore(this);

        setSupportActionBar(toolbar);

        listView = findViewById(R.id.notesList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
//                Uri uri = Uri.parse(NotesProvider.CONTENT_URI + "/" + id);
//                intent.putExtra(NotesProvider.CONTENT_ITEM_TYPE, uri);
//                startActivityForResult(intent, EDITOR_REQUEST_CODE);

                System.out.println("Clicked item" + id);
                System.out.println("Clicked the fab");
                startActivity(new Intent(MainActivity.this, EditorActivity.class));
            }
        });

//        getLoaderManager().initLoader(0, null, this);


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
    public void onClick(View view) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.app_bar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                userLocalStore.clear();
                userLocalStore.setUserLoggedIn(false);
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean authenticate() {
        return userLocalStore.isUserLoggedIn();
    }

    private void displayUserNotes() {
        ServerRequest serverRequest = new ServerRequest(restTemplate);
        final User user = userLocalStore.getLoggedInUser();

        serverRequest.retrieveAllNotesForUser(user, new UserCallback<List<Note>>() {
            @Override
            public void done(List<Note> notes) {
                NotesAdapter notesAdapter = new NotesAdapter(MainActivity.this, android.R.layout.activity_list_item, notes);
                listView.setAdapter(notesAdapter);
            }
        });
    }

    private void openEditorNewNote() {
        FloatingActionButton fab = findViewById(R.id.fab_newNote);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                System.out.println("Clicked the fab");
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
//                startActivityForResult(intent, EDITOR_REQUEST_CODE);
            }
        });
    }
}
