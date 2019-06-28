package com.fmi.notesmanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fmi.notesmanager.R;
import com.fmi.notesmanager.model.ServerRequest;
import com.fmi.notesmanager.model.User;
import com.fmi.notesmanager.model.UserCallback;

import org.springframework.web.client.RestTemplate;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    final RestTemplate restTemplate = new RestTemplate();

    private EditText etUsername;
    private EditText etEmail;
    private EditText etPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRegister:
                final String username = etUsername.getText().toString();
                final String email = etEmail.getText().toString();
                final String password = etPassword.getText().toString();
                User user = new User(null, username, password, email);
                ServerRequest serverRequest = new ServerRequest(restTemplate);
                serverRequest.storeUserDataInBackground(user, new UserCallback<User>() {
                    @Override
                    public void done(User returnedUser) {
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    }
                });
                break;
            case R.id.tvLoginLink:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }
}
