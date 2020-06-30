package com.example.brandstore.FavouriteRoomData;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FavouriteDao {
    @Insert
    void insert(FavouriteData data);
    @Update
    void update(FavouriteData data);
    @Delete
    void delete(FavouriteData data);
    @Query("DELETE FROM favourite_table")
    void deleteAllNotes();
    @Query("SELECT * FROM favourite_table ORDER BY id DESC")
    LiveData<List<FavouriteData>> getAllNotes();
}
