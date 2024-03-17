package com.akatsuki.gps_app_front.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.akatsuki.gps_app_front.data.model.entity.AuthenToken;
import com.akatsuki.gps_app_front.data.repository.AuthenTokenDao;

@Database(entities = {AuthenToken.class}, version = 1)
public abstract class AuthTokenDataBase extends RoomDatabase {
    public abstract AuthenTokenDao authenTokenDao();
}
