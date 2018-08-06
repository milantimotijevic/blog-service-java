package com.blog.controller;

import com.blog.domain.Category;
import com.blog.domain.Post;
import com.blog.domain.Tag;
import com.blog.domain.User;
import com.blog.dto.CreatePostDto;
import com.blog.dto.GetUserByEmailDto;
import com.blog.errorcodes.ErrorCodes;
import com.blog.repository.*;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

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
    public CreatePostDto createPost(@RequestBody Post post, Authentication authentication) throws NotFoundException {
        if(post.getCategory() == null) {
            throw new NotFoundException(ErrorCodes.POST_MISSING_CATEGORY);
        }
        //TODO utilize CascadeType in order to avoid saving each object individually
        Category category = categoryRepository.getOneByText(post.getCategory().getText());
        if(category == null) {
            throw new NotFoundException(ErrorCodes.POST_MISSING_CATEGORY);
        }
        User user = userRepository.getOneByEmail(authentication.getPrincipal().toString());
        user.addPost(post);
        category.addPost(post);
        post.setCategory(category);
        post.setUser(user);

//        List<Tag> tags = post.getTags();
//        for(Tag tag : tags) {
//            Tag tempTag = tagRepository.getOneByText(tag.getText());
//            if(tempTag != null) { //if it already exists, we don't want to create another entry
//                tag = tempTag;
//            }
//            tag.getPosts().add(post);
//            post.getTags().add(tag);
//            userRepository.save(user);
//            categoryRepository.save(category);
//            postRepository.save(post);
//            tagRepository.save(tag);
//            postRepository.save(post);
//        }
        post.setTags(null); //TODO handle tags
        userRepository.save(user);
        categoryRepository.save(category);
        postRepository.save(post);
        return new CreatePostDto(post);
    }

    @RequestMapping(value = "/getallposts")
    public List<Post> getAllPublishedPosts() {
        List<Post> posts = postRepository.getAllByPublished(true);
        return posts;
    }

    @RequestMapping(value = "/getownposts")
    public List<Post> getAllPublishedPostsByUser(@RequestParam(value = "published", required = false, defaultValue = "false") boolean published, Authentication authentication) {
        User user = userRepository.getOneByEmail(authentication.getPrincipal().toString());
        return postRepository.getAllByUserAndPublished(user, published);
    }

}
