package com.fmi.notesmanager.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.io.Serializable;

/**
 * The object representation of the note.
 *
 * @author angel.beshirov
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Note implements Serializable {

    private Long id;
    private String title;
    private String content;

    public Note() {

    }

    public Note(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    @JsonSetter("id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonSetter("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonSetter("content")
    public void setContent(String content) {
        this.content = content;
    }

    @JsonGetter("id")
    public Long getId() {
        return id;
    }

    @JsonGetter("title")
    public String getTitle() {
        return title;
    }

    @JsonGetter("content")
    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
