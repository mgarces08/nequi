package com.nequi.franchise.exceptions;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ApiException extends Exception {

    private static final long serialVersionUID = 1L;
    private final String code;
    private final String description;
    private Integer statusCode = 500;

    public ApiException(String code, String description, Integer statusCode) {
        this.code = code;
        this.description = description;
        this.statusCode = statusCode;
    }

    public ApiException(String code, String description, Throwable cause) {
        super(cause);
        this.code = code;
        this.description = description;
    }

    public String toJson() {
        Map<String, Object> json = HashMap.newHashMap(3);
        json.put("error", this.code);
        json.put("message", this.description);
        json.put("status", this.statusCode);
        return json.toString();
    }
}