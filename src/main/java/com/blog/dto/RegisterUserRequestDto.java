package com.blog.dto;

import com.blog.domain.User;

import java.io.Serializable;

public class RegisterUserRequestDto implements Serializable {
    private String firstname;
    private String lastname;
    private String email;
    private String password;


    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RegisterUserRequestDto() {}

    public RegisterUserRequestDto(String firstname, String lastname, String email, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public User toUser() {
       return new User(this.getFirstname(), this.getLastname(), this.getEmail(), this.getPassword());
    }
}
