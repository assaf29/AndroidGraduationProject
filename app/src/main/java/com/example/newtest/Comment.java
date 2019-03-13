package com.example.newtest;

public class Comment {

    private String content ;
    private String id;


    private String createdUser;
    private String date;

    public Comment (){

    }

    public Comment (String id, String content, String date, String createdUser){
        this.id =id;
        this.content = content;
        this.date = date;
        this.createdUser = createdUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

}
