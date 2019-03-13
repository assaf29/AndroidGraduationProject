package com.example.newtest;

public class AcceptedOffer {

    String employer;
    String jobSeeker;
    String postID;
    String postTitle;
    String id ;


    public AcceptedOffer(String id ,String employer, String jobSeeker, String postID , String postTitle) {
       this.id = id;
        this.employer = employer;
        this.jobSeeker = jobSeeker;
        this.postID = postID;
        this.postTitle = postTitle;
    }

    public AcceptedOffer() {
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getJobSeeker() {
        return jobSeeker;
    }

    public void setJobSeeker(String jobSeeker) {
        this.jobSeeker = jobSeeker;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
