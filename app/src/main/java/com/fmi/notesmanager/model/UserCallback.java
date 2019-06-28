package com.fmi.notesmanager.model;

public interface UserCallback<T> {

    void done(final T returnedUser);
}
