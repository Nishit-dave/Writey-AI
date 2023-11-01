package com.app.writy.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM roomuser")
    List<RoomUser> getAllUsers();
    @Insert
    void insertUser(RoomUser... users);
    @Delete
    void delete(RoomUser user);
    @Query("DELETE FROM roomUser WHERE uid = :userId")
    abstract void deleteByUserId(long userId);
}
