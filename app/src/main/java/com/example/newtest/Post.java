package com.example.newtest;

public class Post {


    private String id;
    private String title;
    private String content;
    private String date;
    private String state;
    private String createdUser;
    private String category;
    private String price;

    public Post(){

    }


    public Post(String id, String title, String content , String category , String date , String createdUser , String state , String price) {

        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.date = date;
        this.createdUser = createdUser;
        this.state = state;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}

