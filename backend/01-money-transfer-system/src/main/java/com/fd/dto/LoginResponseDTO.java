package com.fd.dto;

public class LoginResponseDTO {
    
    private long accountId;
    private String username;
    private String token;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(long accountId, String username, String token) {
        this.accountId = accountId;
        this.username = username;
        this.token = token;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
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