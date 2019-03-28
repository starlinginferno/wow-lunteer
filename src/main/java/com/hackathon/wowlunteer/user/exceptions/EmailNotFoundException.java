package com.hackathon.wowlunteer.user.exceptions;

public class EmailNotFoundException extends Exception {

    public EmailNotFoundException(String message) {
        super(message);
    }

    public EmailNotFoundException() {
    }
}
