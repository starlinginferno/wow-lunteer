package com.hackathon.wowlunteer.file.exception;

public class FileStorageException extends Exception{

    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
