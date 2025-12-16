package com.pasteleria.backend.dto.transbank;

public class CreateTransactionResponse {
    private String url;
    private String token;

    public CreateTransactionResponse() {}

    public CreateTransactionResponse(String url, String token) {
        this.url = url;
        this.token = token;
    }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
