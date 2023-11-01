package com.app.writy.roomdb;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class RoomUser {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @ColumnInfo(name = "category")
    public String category;
    @ColumnInfo(name = "allData")
    public String allData;
    @ColumnInfo(name = "query")
    public String query;
    @ColumnInfo(name = "title")
    public String title;
}