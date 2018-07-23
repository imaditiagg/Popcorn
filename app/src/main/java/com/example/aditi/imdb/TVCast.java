package com.example.aditi.imdb;

public class TVCast {

    String name;
    String character;
    String poster_path;
    int id;

    public String getName() {
        return name;
    }

    public String getCharacter() {
        return character;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setId(int id) {
        this.id = id;
    }
}
