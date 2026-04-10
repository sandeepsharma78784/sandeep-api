package com.sandeep.application.dto;

/*
jo bhi data rhega vo tme aayega baaki chize to fixed rhegi status and message
 */
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class APIResponseFormat<T> {

    private int status;
    private String message;
    private T data;
    private String error;

    public APIResponseFormat(int status, String message, T data,String error) {
        this.status = status; // this is for status code like 200, 404, etc.
        this.message = message;
        this.data = data;
        this.error = error; // error will be null when the response is successful
    }

    // getters & setters
}