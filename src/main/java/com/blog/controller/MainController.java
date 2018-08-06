package com.blog.controller;

import com.blog.domain.*;
import com.blog.dto.CreatePostDto;
import com.blog.dto.GetPostDto;
import com.blog.dto.GetUserByEmailDto;
import com.blog.dto.RegisterUserRequestDto;
import com.blog.errorcodes.ErrorCodes;
import com.blog.repository.*;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @RequestMapping(value = "/register", method = RequestMethod.POST) //TODO properly SQL exception in case of trying to register a user with existing username (email)
    public User registerUser(@RequestBody RegisterUserRequestDto userDto) {
        User user = userDto.toUser();
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
    public List<GetPostDto> getAllPublishedPosts() {
        List<Post> posts = postRepository.getAllByPublished(true);
        return posts.stream().map(GetPostDto::new).collect(Collectors.toList());
    }

    @RequestMapping(value = "/getownposts")
    public List<Post> getAllPublishedPostsByUser(@RequestParam(value = "published", required = false, defaultValue = "false") boolean published, Authentication authentication) {
        User user = userRepository.getOneByEmail(authentication.getPrincipal().toString());
        return postRepository.getAllByUserAndPublished(user, published);
    }

    @RequestMapping(value = "/editpost", method = RequestMethod.PUT)
    public Post editPost(@RequestBody Post post, Authentication authentication) throws NotFoundException { //only drafts can be edited; we determine whether a Post is a draft or not by 'published' flag (in other words there's no Draft entity)
        User user = userRepository.getOneByEmail(authentication.getPrincipal().toString());
        Post tempPost = postRepository.getOneByUserAndId(user, post.getId());
        if(tempPost == null) { //means user is trying to edit a post they did not create, or the post simply does not exist
            throw new NotFoundException(ErrorCodes.POST_USER_MISMATCH); //TODO create wrapper so I can throw appropriate exception with appropriate error message
        }
        if(tempPost.isPublished()) {
            throw new NotFoundException(ErrorCodes.NOT_DRAFT);
        }
        //good to go with editing post; only want to allow editing of basic properties
        tempPost.setTags(post.getTags());
        tempPost.setCategory(post.getCategory());
        tempPost.setBody(post.getBody());
        tempPost.setTitle(post.getTitle());

        return postRepository.save(tempPost);
    }

    @RequestMapping(value = "/rate/{postId}/{rating}")
    public Post ratePost(@PathVariable int postId, @PathVariable int rating, Authentication authentication) throws NotFoundException {
        if(rating < 1 || rating > 10) {
            throw new NotFoundException(ErrorCodes.RATING_OUT_OF_BOUNDS);
        }
        Post post = postRepository.getOneByIdAndPublished(postId, true);
        if(post == null) {
            throw new NotFoundException(ErrorCodes.POST_NOT_FOUND); //could also mean the user is trying to rate their own draft (rating one's own published post is disabled below)
        }
        User user = userRepository.getOneByEmail(authentication.getPrincipal().toString());
        if(post.getUser().getId() == user.getId()) { //means the user is trying to rate their own post (naughty naughty!)
            throw new NotFoundException(ErrorCodes.CANNOT_RATE_OWN_POST);
        }
        for(Rating currentRating : post.getRatings()) {
            if(currentRating.getUser().getId() == user.getId()) { //means the user has already rated this post
                throw new NotFoundException(ErrorCodes.ALREADY_RATED);
            }
        }
        Rating newRating = new Rating(rating, user, post);
        ratingRepository.save(newRating);
        return postRepository.save(post);
    }
}
