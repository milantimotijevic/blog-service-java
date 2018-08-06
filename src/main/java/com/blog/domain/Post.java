package com.blog.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Post implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String body;
    @ManyToMany
    private List<Tag> tags = new ArrayList<>();
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();
    @OneToMany(mappedBy = "post")
    @JsonBackReference
    private List<Rating> ratings = new ArrayList<>();
    @ManyToOne
    private Category category;
    private boolean published;

    public Post() {}

    public Post(String title, String body, Category category) {
        this.setTitle(title);
        this.setBody(body);
        this.setCategory(category);
    }

    public Post(String title, String body, Category category, boolean published) {
        this.setTitle(title);
        this.setBody(body);
        this.setCategory(category);
        this.setPublished(published);
    }

    public Post(String title, String body, Category category, boolean published, List<Tag> tags) {
        this.setTitle(title);
        this.setBody(body);
        this.setCategory(category);
        this.setPublished(published);
        this.setTags(tags);
    }

    public List<Comment> getReplies() {
        return getComments();
    }

    public void setReplies(List<Comment> comments) {
        this.setComments(comments);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addComment(Comment comment) {
        getComments().add(comment);
    }

    public void addTag(Tag tag) {
        getTags().add(tag);
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addRating(Rating rating) {
        this.ratings.add(rating);
    }
}
