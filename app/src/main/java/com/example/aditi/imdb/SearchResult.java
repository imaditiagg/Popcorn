package com.example.aditi.imdb;

public class SearchResult {

  private   int id;
  private   String poster_path;
  private   String profile_path;
  private   String title;
  private   String name;
  private   String release_date;
  private   String first_air_date;
  private   String media_type;
  String overview;

    public int getId() {
        return id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }
}
