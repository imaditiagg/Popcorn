package com.example.aditi.imdb;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MovieDao {

    //movies
    @Insert
    void addMovie(Movie movie);

    @Query("select * from favmoviestable where id = :id")
    Movie search(int id);

    @Query("DELETE FROM favmoviestable WHERE id = :id")
    void deleteMovie(int id);

    @Query("select * from favmoviestable")
    List<Movie> loadMovies();


    //tv shows
    @Insert
    void addShow(TV tvShow);

    @Query("select * from favtvshowstable where id = :id")
    TV searchShow(int id);

    @Query("DELETE FROM favtvshowstable WHERE id = :id")
    void deleteShow(int id);

    @Query("select * from favtvshowstable")
    List<TV> loadShows();


}
