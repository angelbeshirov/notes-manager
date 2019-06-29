package com.fmi.notesmanager.interaction;

/**
 * This functional interface is used to handle the result from the async task when it completes.
 *
 * @param <T> the type of the result from the task
 * @author angel.beshirov
 */
@FunctionalInterface
public interface Callback<T> {

    void done(final T result);
}