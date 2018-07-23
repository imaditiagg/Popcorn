package com.example.aditi.imdb;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface MovieDao {

    @Insert
    void addMovie(Movie movie);

    @Query("select * from favmoviestable where id = :id")
    Movie search(int id);

    @Query("DELETE FROM favmoviestable WHERE id = :id")
    void deleteMovie(int id);
}
