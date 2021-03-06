package com.example.newtest;

import java.util.Date;

public class ChatMessage {


    private String messageText;
    private String messageUser;
    private long messageTime;
    private String messageID;



    public ChatMessage(String messageText, String messageUser , String messageID) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.messageID = messageID;

        // Initialize to current time
        messageTime = new Date().getTime();
    }

    public ChatMessage(){

    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }


}
