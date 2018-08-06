package com.blog.errorcodes;

public class ErrorCodes {
    public static final String POST_MISSING_CATEGORY = "Post must have a category";
    public static final String POST_USER_MISMATCH = "Unable to find post under specified user";
    public static final String NOT_DRAFT = "Unable to edit a published post (only drafts can be edited)";
    public static final String CANNOT_RATE_OWN_POST = "You may not rate your own post";
    public static final String RATING_OUT_OF_BOUNDS = "Rating may only be within 1-10 range";
    public static final String POST_NOT_FOUND = "Unable to find post with specified id";
    public static final String ALREADY_RATED = "You have already rated this post";
}
