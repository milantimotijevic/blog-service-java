package com.blog.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Rating implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int value;
    @ManyToOne
    @JsonBackReference
    private User user; //giver of rating
    @ManyToOne
    //@JsonBackReference
    private Post post;

    public Rating() {}

    public Rating(int value) {
        this.value = value;
    }

    public Rating(int value, User user, Post post) {
        this.setValue(value);
        this.setUser(user);
        this.setPost(post);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
