package com.akatsuki.gps_app_front.data.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.akatsuki.gps_app_front.data.model.entity.AuthenToken;

import java.util.List;

import retrofit2.http.GET;

@Dao
public interface AuthenTokenDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void addToken(AuthenToken token);

    @Delete
    public void deleteToken(AuthenToken token);

    @Query("Select * from authentication_token")
    public List<AuthenToken> getAllToken();

    @Update
    public void updateToken(AuthenToken token);

    @Query("Select * from authentication_token where id= :tokenId")
    public AuthenToken getAuthToken(Long tokenId);
}
