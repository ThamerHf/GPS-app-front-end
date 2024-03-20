package com.akatsuki.gps_app_front.data.model.dto.response;

public class GenericResponseDto {
    private String message;

    public GenericResponseDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
