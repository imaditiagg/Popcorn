package com.example.aditi.imdb;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

@Dao
public interface MovieDao {

    @Insert
    void addMovie(Movie movie);
}
