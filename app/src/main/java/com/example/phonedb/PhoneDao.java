package com.example.phonedb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PhoneDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Phone phone);

    @Query("SELECT * FROM phones")
    LiveData<List<Phone>> getAllPhones();

    @Query("SELECT * FROM phones ORDER BY manufacturer ASC, model ASC")
    LiveData<List<Phone>> getPhones();

    @Insert
    void insertAll(Phone... phones);

    @Delete
    void delete(Phone phone);

    @Update
    void update(Phone phone);

    @Query("SELECT * FROM phones ORDER BY manufacturer ASC, model ASC")
    LiveData<List<Phone>> getAlphabetizedPhones();

    @Query("DELETE FROM phones")
    void deleteAll();
}
