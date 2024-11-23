package com.election.electionbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class ForumPost {


    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    private String tag; // tijdelijk een string //

    @Column(nullable = false)
    private String author; // tijdelijk een string //

    @Column(nullable = false)
    private String textContent;

    private LocalDateTime creationDate;

    public ForumPost() {

    }

    public ForumPost(Long id, String title, String tag, String author, String textContent, LocalDateTime creationDate) {
        this.id = id;
        this.title = title;
        this.tag = tag;
        this.author = author;
        this.textContent = textContent;
        this.creationDate = creationDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
