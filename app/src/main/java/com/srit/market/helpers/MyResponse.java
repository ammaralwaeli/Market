package com.srit.market.helpers;

public class MyResponse<T> {

    private T posts;
    private String error;

    public MyResponse(T posts) {
        this.posts = posts;
        this.error = null;
    }

    public MyResponse(String error) {
        this.error = error;
        this.posts = null;
    }

    public T getPosts() {
        return posts;
    }

    public String getError() {
        return error;
    }
}
