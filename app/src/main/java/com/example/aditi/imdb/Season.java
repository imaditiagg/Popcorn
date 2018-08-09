package com.example.aditi.imdb;

import java.util.List;

public class Season {

    String air_date;
    List<Episode> episodes;
    String name;

    public String getAir_date() {
        return air_date;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public String getName() {
        return name;
    }

    public void setAir_date(String air_date) {
        this.air_date = air_date;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }

    public void setName(String name) {
        this.name = name;
    }
}

