package com.fmi.notesmanager.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.io.Serializable;


/**
 * The object representation of the user.
 *
 * @author angel.beshirov
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {

    private Integer id;
    private String username;
    private String password;
    private String email;

    public User() {
    }

    public User(final Integer id, final String username, final String password, final String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @JsonGetter("id")
    public Integer getId() {
        return id;
    }

    @JsonGetter("username")
    public String getUsername() {
        return username;
    }

    @JsonGetter("password")
    public String getPassword() {
        return password;
    }

    @JsonGetter("email")
    public String getEmail() {
        return email;
    }

    @JsonSetter("id")
    public void setId(final Integer id) {
        this.id = id;
    }

    @JsonSetter("username")
    public void setUsername(final String username) {
        this.username = username;
    }

    @JsonSetter("password")
    public void setPassword(final String password) {
        this.password = password;
    }

    @JsonSetter("email")
    public void setEmail(final String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
