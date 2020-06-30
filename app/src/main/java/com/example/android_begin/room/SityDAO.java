package com.example.android_begin.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface SityDAO {
    @Query("SELECT * FROM city")
    Flowable<List<City>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> insert(City city);

    @Update
    void update(City city);

    @Delete
    Single<Integer> delete(City city);

}
