package com.nequi.franchise.nequi.exceptions;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class BadRequestException extends RuntimeException {

    private final String message;
    private final Integer statusCode = 400;

    public BadRequestException(String message) {
        super(message);
        this.message = message;
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public String toJson() {
        Map<String, Object> json = HashMap.newHashMap(2);
        json.put("message", this.message);
        json.put("status", this.statusCode);
        return json.toString();
    }
}