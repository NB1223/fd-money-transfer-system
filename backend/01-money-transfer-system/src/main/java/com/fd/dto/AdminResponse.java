package com.fd.dto;

public class AdminResponse {

    
    private long userId;
    private String username;
    private String token;

    public AdminResponse() {
    }

    public AdminResponse(long userId, String username, String token) {
        this.userId = userId;
        this.username = username;
        this.token = token;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}