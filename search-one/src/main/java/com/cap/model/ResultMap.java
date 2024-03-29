package com.cap.model;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class ResultMap extends HashMap<String, Object> {
    public ResultMap() {
    }

    public ResultMap success() {
        this.put("result", "success");
        return this;
    }

    public ResultMap fail() {
        this.put("result", "fail");
        return this;
    }

    public ResultMap code(int code) {
        this.put("code", code);
        return this;
    }

    public ResultMap token(Object token) {
        this.put("token", token);
        return this;
    }

    public ResultMap message(String name, Object message) {
        this.put(name, message);
        return this;
    }
}
