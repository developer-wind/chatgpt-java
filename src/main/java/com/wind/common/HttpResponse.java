package com.wind.common;

public class HttpResponse {

    public HttpResponse(int code, String data) {
        this.code = code;
        this.data = data;
    }

    public HttpResponse(int code) {
        this.code = code;
    }

    private int code;
    private String data;

    public int getCode() {
        return code;
    }

    public String getData() {
        return data;
    }
}
