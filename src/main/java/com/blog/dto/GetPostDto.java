package com.blog.dto;

import com.blog.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GetPostDto {
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
        this.title = post.getTitle();
        this.body = post.getBody();
        this.tags = post.getTags();
        this.category = post.getCategory();
        this.comments = post.getComments();
        this.userFirstname = post.getUser().getFirstname();
        this.userLastname = post.getUser().getLastname();
        this.averageRating = post.getRatings().stream().mapToDouble(Rating::getValue).average().orElse(0);

    }
}
