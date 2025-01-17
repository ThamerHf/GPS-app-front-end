package com.akatsuki.gps_app_front.data.model.dto.request;

public class LoginRequestDto {

    public LoginRequestDto(String userName, String pwd) {
        this.userName = userName;
        this.pwd = pwd;
    }

    public LoginRequestDto() {
    }

    private String userName;

    private String pwd;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
