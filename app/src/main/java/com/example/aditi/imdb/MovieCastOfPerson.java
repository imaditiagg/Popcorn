package com.example.aditi.imdb;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieCastOfPerson {

    @SerializedName("cast")
    public ArrayList<MovieCast> movieCasts;
}
