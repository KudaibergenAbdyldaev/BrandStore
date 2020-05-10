package com.example.brandstore.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BasketDao {

    @Insert
    void insert(BasketData data);
    @Update
    void update(BasketData data);
    @Delete
    void delete(BasketData data);
    @Query("DELETE FROM basket_table")
    void deleteAllNotes();
    @Query("SELECT * FROM basket_table ORDER BY id DESC")
    LiveData<List<BasketData>> getAllNotes();

}
