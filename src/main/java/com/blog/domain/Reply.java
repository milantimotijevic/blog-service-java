package com.blog.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Reply implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String text;
    @ManyToOne
    private User user;
    @ManyToOne
    private Comment comment;

    //TODO private List<Reply> replies - handle @ManyToMany on self, bonus points

    public Reply() {}

    public Reply(String text, Comment comment) {
        this.setText(text);
        this.setComment(comment);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
