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
import com.fmi.notesmanager.interaction.UserLocalStore;
import com.fmi.notesmanager.model.User;

import org.springframework.web.client.RestTemplate;

/**
 * The login activity which overrides {@link AppCompatActivity#onCreate(Bundle)}
 * and {@link View.OnClickListener#onClick(View)}.
 *
 * @author angel.beshirov
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private final RestTemplate restTemplate = new RestTemplate();

    private EditText etUsername;
    private EditText etPassword;
    private UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        userLocalStore = new UserLocalStore(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                User user = new User(null, username, password, null);
                System.out.println("Starting logging in, sending user: " + user.toString());
                authenticate(user);
                break;
            case R.id.tvRegisterLink:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }

    private void authenticate(User user) {
        ServerRequest serverRequest = new ServerRequest(restTemplate);
        serverRequest.fetchUserDataInBackground(user, new Callback<User>() {
            @Override
            public void done(User result) {
                if (result == null) {
                    showErrorMessage();
                } else {
                    loginUser(result);
                }
            }
        });
    }

    private void showErrorMessage() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginActivity.this);
        dialogBuilder.setMessage("Incorrect user details");
        dialogBuilder.setPositiveButton("OK", null);
        dialogBuilder.show();
    }

    private void loginUser(User user) {
        userLocalStore.storeUserData(user);
        userLocalStore.setUserLoggedIn(true);

        startActivity(new Intent(this, MainActivity.class));
    }
}
