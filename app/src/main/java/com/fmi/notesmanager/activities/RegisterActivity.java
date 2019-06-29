package com.fmi.notesmanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.fmi.notesmanager.R;
import com.fmi.notesmanager.interaction.Callback;
import com.fmi.notesmanager.interaction.ServerRequest;
import com.fmi.notesmanager.model.User;

import org.springframework.web.client.RestTemplate;

import java.util.Objects;

/**
 * The register activity which overrides {@link AppCompatActivity#onCreate(Bundle)}
 * and {@link View.OnClickListener#onClick(View)}. Does basic validation of the password
 * whether they are at least 6 characters long and that they match.
 *
 * @author angel.beshirov
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private final RestTemplate restTemplate = new RestTemplate();

    private EditText etUsername;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etRepeatPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etRepeatPassword = findViewById(R.id.etRepeatPassword);

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
                if (validate()) {
                    User user = new User(null, username, password, email);
                    ServerRequest serverRequest = new ServerRequest(restTemplate);
                    serverRequest.storeUserDataInBackground(user, new Callback() {
                        @Override
                        public void done(Object result) {
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        }
                    });
                }
                break;
            case R.id.tvLoginLink:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }

    private boolean validate() {
        final String password = etPassword.getText().toString();
        final String repeatPassword = etRepeatPassword.getText().toString();

        if (password.length() < 6) {
            makeDialog("Password length must be at least 6 characters!");
            return false;
        }

        if (!Objects.equals(password, repeatPassword)) {
            makeDialog("Passwords don't match!");
            return false;
        }

        return true;
    }

    private void makeDialog(String message) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(RegisterActivity.this);
        dialogBuilder.setMessage(message);
        dialogBuilder.setPositiveButton("OK", null);
        dialogBuilder.show();
    }
}
