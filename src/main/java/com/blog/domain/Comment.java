package com.blog.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Comment implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String text;
    @ManyToOne
    @JsonBackReference
    private Post post;
    @ManyToOne
    private User user;
    private Date date;
    @OneToMany(mappedBy = "comment")
    private List<Reply> replies = new ArrayList<>();

    public Comment() {}

    public Comment(String text, Post post, User user, Date date) {
        this.setText(text);
        this.setPost(post);
        this.setUser(user);
        this.setDate(date);
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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) { //don't think I'll need this one, but let's leave it just in case
        this.replies = replies;
    }

    public void addReply(Reply reply) {
        this.getReplies().add(reply);
    }
}
