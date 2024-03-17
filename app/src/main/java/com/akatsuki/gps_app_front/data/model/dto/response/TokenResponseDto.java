package com.akatsuki.gps_app_front.data.model.dto.response;

public class TokenResponseDto {

    private String token;

    public TokenResponseDto(String token) {
        this.token = token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
