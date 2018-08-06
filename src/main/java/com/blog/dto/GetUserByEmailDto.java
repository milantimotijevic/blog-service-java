package com.blog.dto;

import java.io.Serializable;

public class GetUserByEmailDto implements Serializable{
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
