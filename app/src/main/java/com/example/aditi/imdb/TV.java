package com.example.aditi.imdb;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "FavTVShowsTable")
public class TV {
    @PrimaryKey(autoGenerate = true)
    public int uniqueId;

    int id;
    String name;
    String poster_path;

    @Ignore
    String first_air_date;
    @Ignore
    String overview;
    @Ignore
    String backdrop_path;
    @Ignore
    float vote_average;

    @Ignore
    List<Genres> genres;

    @Ignore
    List<Integer>  episode_run_time;

    TV(int id,String name,String poster_path){
        this.id=id;
        this.name=name;
        this.poster_path=poster_path;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public String getOverview() {
        return overview;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public float getVote_average() {
        return vote_average;
    }
}
