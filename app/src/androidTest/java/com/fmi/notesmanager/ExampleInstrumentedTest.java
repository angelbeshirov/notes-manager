package com.fmi.notesmanager;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.fmi.notesmanager.model.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    public static final String SERVER_ADDRESS = "http://localhost/register.php";

    @Test
    public void useAppContext() {
        RestTemplate restTemplate = new RestTemplate();
        User user = new User("angel", "123456", "test123@abv.bg");
        HttpEntity<User> request = new HttpEntity<>(user);
        restTemplate.postForObject(SERVER_ADDRESS, request, User.class);
    }
}
