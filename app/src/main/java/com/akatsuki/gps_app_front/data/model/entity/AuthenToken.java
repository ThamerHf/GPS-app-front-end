package com.akatsuki.gps_app_front.data.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "authentication_token")
public class AuthenToken {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "auth_token")
    private String token;

    public AuthenToken(Long id, String token) {
        this.id = id;
        this.token = token;
    }

    public AuthenToken() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
