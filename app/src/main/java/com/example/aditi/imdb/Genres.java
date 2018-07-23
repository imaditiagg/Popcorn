package com.example.aditi.imdb;


import com.google.gson.annotations.SerializedName;


public class Genres {


    int id;
    String name;

    public void setName(String name) {
        this.name = name;
    }

    public void setGenreId(int genreId) {

        this.id = genreId;
    }

    public int getGenreId() {

        return id;
    }

    public String getName() {
        return name;
    }
}
