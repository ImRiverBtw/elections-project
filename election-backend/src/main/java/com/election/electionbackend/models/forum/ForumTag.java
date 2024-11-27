/**
 * ForumTag entity
 * Used when assigning tags to a forum post
 */

package com.election.electionbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class ForumTag {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public ForumTag() {

    }

    public ForumTag(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ForumTag(String name) {
        this.name = name;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
