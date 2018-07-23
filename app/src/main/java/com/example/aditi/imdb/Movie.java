package com.example.aditi.imdb;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


@Entity(tableName = "FavMoviesTable")
public class Movie {
    @PrimaryKey(autoGenerate = true)
    public int uniqueId;

    int id;
    String title;
    @SerializedName("poster_path")
    String posterPath;

    @SerializedName("backdrop_path") @Ignore
    String backdropPath;
    @SerializedName("vote_average") @Ignore
    float rating;
    @SerializedName("release_date")
    @Ignore
    String releaseDate;
    @Ignore
    String overview;
    @Ignore
    String tagline;
    @Ignore
    int runtime;

    @Ignore
    public ArrayList<Genres> genres =new ArrayList<>();

    public int getMovieId() {
        return id;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public String getTagline() {
        return tagline;
    }

    public int getRuntime() {
        return runtime;
    }





    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public float getRating() {
        return rating;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setMovieId(int movieId) {
        this.id = movieId;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }


}
