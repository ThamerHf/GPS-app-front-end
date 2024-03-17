package com.akatsuki.gps_app_front.data.database.databaseClient;

import android.content.Context;

import androidx.room.Room;

import com.akatsuki.gps_app_front.data.database.AuthTokenDataBase;

public class AuthTokenDataBaseClient {
    private static AuthTokenDataBase INSTANCE;

    public static AuthTokenDataBase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AuthTokenDataBase.class, "authtoken-database")
                    .build();
        }
        return INSTANCE;
    }

}
