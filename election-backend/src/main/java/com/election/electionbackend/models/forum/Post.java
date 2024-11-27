/**
 * ForumPost entity
 */

package com.election.electionbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class Post {

    /**
     * Attributes
     */

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    private String tag; // tijdelijk een string, moet entiteit tag worden//

    @Column(nullable = false)
    private String author; // tijdelijk een string, moet entiteit user.id worden//

    @Column(nullable = false)
    private String textContent;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime creationDate;

    public Post() {

    }

    /**
     * Constructor for creating a new ForumPost Object
     * @param id - the id of post
     * @param title - the title of the post
     * @param tag - the tags that are associated with the post
     * @param author - the author of the post
     * @param textContent - the content of the post
     * @param creationDate - the time when the post is created
     */
    public Post(Long id, String title, String tag, String author, String textContent, LocalDateTime creationDate) {
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
