package com.blog.dto;

import com.blog.domain.*;

import java.io.Serializable;
import java.util.List;

public class GetPostDto implements Serializable{
    private String title;
    private String body;
    private List<Tag> tags;
    private Category category;
    private List<Comment> comments;
    private String userFirstname;
    private String userLastname;
    private double averageRating;

    public GetPostDto() {}

    public GetPostDto(Post post) {
        this.setTitle(post.getTitle());
        this.setBody(post.getBody());
        this.setTags(post.getTags());
        this.setCategory(post.getCategory());
        this.setComments(post.getComments());
        this.setUserFirstname(post.getUser().getFirstname());
        this.setUserLastname(post.getUser().getLastname());
        this.setAverageRating(post.getRatings().stream().mapToDouble(Rating::getValue).average().orElse(0));
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

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getUserFirstname() {
        return userFirstname;
    }

    public void setUserFirstname(String userFirstname) {
        this.userFirstname = userFirstname;
    }

    public String getUserLastname() {
        return userLastname;
    }

    public void setUserLastname(String userLastname) {
        this.userLastname = userLastname;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }
}
