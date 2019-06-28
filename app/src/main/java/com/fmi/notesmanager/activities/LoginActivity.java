package com.fmi.notesmanager.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fmi.notesmanager.R;
import com.fmi.notesmanager.model.ServerRequest;
import com.fmi.notesmanager.model.User;
import com.fmi.notesmanager.model.UserCallback;
import com.fmi.notesmanager.model.UserLocalStore;

import org.springframework.web.client.RestTemplate;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    final RestTemplate restTemplate = new RestTemplate();

    Button btnLogin;
    EditText etUsername;
    EditText etPassword;
    TextView tvRegisterLink;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        tvRegisterLink = findViewById(R.id.tvRegisterLink);
        userLocalStore = new UserLocalStore(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btnLogin:
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                User user = new User(username, password, null);
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
        serverRequest.fetchUserDataInBackground(user, new UserCallback() {

            @Override
            public void done(User returnedUser) {
                System.out.println("Done logging in");
                System.out.println("Returned user is" + returnedUser);
                if(returnedUser == null) {
                    showErrorMessage();
                } else {
                    loginUser(returnedUser);
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
