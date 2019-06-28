package com.fmi.notesmanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fmi.notesmanager.R;
import com.fmi.notesmanager.model.User;
import com.fmi.notesmanager.model.UserLocalStore;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogout;
    EditText etName;
    EditText etEmail;
    UserLocalStore userLocalStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);

        btnLogout = findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(this);
        userLocalStore = new UserLocalStore(this);
    }


    @Override
    public void onStart() {
        super.onStart();
        if(authenticate()) {
            displayUserDetail();
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogout:
                userLocalStore.clear();
                userLocalStore.setUserLoggedIn(false);
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }

    private boolean authenticate() {
        return userLocalStore.isUserLoggedIn();
    }

    private void displayUserDetail() {
        final User user = userLocalStore.getLoggedInUser();
        etName.setText(user.getUsername());
        etEmail.setText(user.getEmail());
    }
}
