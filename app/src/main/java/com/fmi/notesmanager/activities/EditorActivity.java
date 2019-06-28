package com.fmi.notesmanager.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.EditText;

import com.fmi.notesmanager.R;

public class EditorActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        toolbar = findViewById(R.id.toolbar);
        editText = findViewById(R.id.etEditor);

        editText.setText("NA MASMDAM DOA SDAS OJIAS DASD ASD ASD AS DPYUTKATAKA" +
                "" +
                "" +
                "" +
                "asdasdasdasdasda" +
                "a" +
                "sa" +
                "sd" +
                "asd" +
                "asd");
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.app_bar_menu_editor, menu);

        return true;
    }
}
