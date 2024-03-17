package com.akatsuki.gps_app_front.data.repositories.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.akatsuki.gps_app_front.data.model.entity.AuthenToken;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface AuthenTokenDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public Completable addToken(AuthenToken token);

    @Delete
    public Completable  deleteToken(AuthenToken token);

    @Transaction
    @Query("Select * from authentication_token")
    public Single<List<AuthenToken>> getAllToken();

    @Update
    public Completable updateToken(AuthenToken token);

}
