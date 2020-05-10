package com.warren.wally.controller;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MessageOutDTO {

    private Integer statusCode;

    private String message;

    public static MessageOutDTO ok(String message) {
        MessageOutDTO ret = new MessageOutDTO();
        ret.setStatusCode(200);
        ret.setMessage(message);
        return ret;
    }
}