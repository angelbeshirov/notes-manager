package com.fmi.notesmanager.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Note implements Serializable {

    private Integer id;

    private String title;

    private String content;

    private String datetime;

    public Note() {

    }

    public Note(Integer id, String title, String content, String datetime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.datetime = datetime;
    }

    @JsonSetter("id")
    public void setId(Integer id) {
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

    @JsonSetter("datetime")
    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    @JsonGetter("id")
    public Integer getId() {
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

    @JsonGetter("datetime")
    public String getDatetime() {
        return datetime;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", datetime='" + datetime + '\'' +
                '}';
    }
}
