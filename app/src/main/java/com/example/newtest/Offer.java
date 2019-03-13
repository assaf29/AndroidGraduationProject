package com.example.newtest;

public class Offer {

    String id;
    String offerSender;
    String offerReceiver;
    String date;
    String postTitle;
    String postID;


    public Offer(String id,  String postID, String postTitle  ,String offerSender, String offerReceiver, String date) {
        this.id = id;
        this.offerSender = offerSender;
        this.offerReceiver = offerReceiver;
        this.date = date;
        this.postTitle = postTitle;
        this.postID = postID;
    }




    public Offer(){

    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOfferSender() {
        return offerSender;
    }

    public void setOfferSender(String offerSender) {
        this.offerSender = offerSender;
    }

    public String getOfferReceiver() {
        return offerReceiver;
    }

    public void setOfferReceiver(String offerReceiver) {
        this.offerReceiver = offerReceiver;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }
}
