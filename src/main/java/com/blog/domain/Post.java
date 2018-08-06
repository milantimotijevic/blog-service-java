package com.blog.domain;

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

    public Post(String title, String body, Category category, List<Tag> tags) {
        this.setTitle(title);
        this.setBody(body);
        this.setCategory(category);
        this.setTags(tags);
    }

    public List<Comment> getReplies() {
        return comments;
    }

    public void setReplies(List<Comment> comments) {
        this.comments = comments;
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
        comments.add(comment);
    }
}
