package com.blog.dto;

import com.blog.domain.Post;
import com.blog.domain.Tag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CreatePostDto implements Serializable{
    private String title;
    private String body;
    private List<Tag> tags = new ArrayList<>();
    private boolean published;

    public CreatePostDto() {}

    public CreatePostDto(Post post) {
        this.setTitle(post.getTitle());
        this.setBody(post.getBody());
        this.setTags(post.getTags());
        this.setPublished(post.isPublished());
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

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }
}
