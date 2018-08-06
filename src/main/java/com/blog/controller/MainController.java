package com.blog.controller;

import com.blog.domain.Post;
import com.blog.domain.User;
import com.blog.dto.GetUserByEmailDto;
import com.blog.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MainController {
    //TODO split into multiple controllers, possibly inside 'rest' package

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/test")
    public String test() {
        return "Test successful! Go get 'em, tiger!";
    }

    @RequestMapping(value = "/sensitivedata") //testing purposes only
    public String getSensitiveData() {
        return "This data is extremely sensitive and only an authenticated user may see it!";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public User registerUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public User getUserByEmail(@RequestBody GetUserByEmailDto getUserByEmailDto) {
        //TODO investigate why passing email address as @PathVariable causes userRepository.getOneByEmail to return null
        return userRepository.getOneByEmail(getUserByEmailDto.getEmail());
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public Post createPost(@RequestBody Post post) {
        return postRepository.save(post);
    }

}
