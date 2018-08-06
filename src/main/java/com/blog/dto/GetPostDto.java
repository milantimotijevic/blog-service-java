package com.blog.dto;

import com.blog.domain.Category;
import com.blog.domain.Tag;

import java.util.ArrayList;
import java.util.List;

public class GetPostDto {
    private String title;
    private String body;
    private List<Tag> tags = new ArrayList<>();
    private Category category;
}
