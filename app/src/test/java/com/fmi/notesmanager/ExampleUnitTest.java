package com.fmi.notesmanager;

import com.fmi.notesmanager.model.User;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    public static final String SERVER_ADDRESS = "http://localhost/register.php";
    @Test
    public void addition_isCorrect() {
        RestTemplate restTemplate = new RestTemplate();
        User user = new User("angel", "123456", "test123@abv.bg");
        HttpEntity<User> request = new HttpEntity<>(user);
        restTemplate.postForObject(SERVER_ADDRESS, request, User.class);
    }
}