
package com.example.aditi.imdb;

import java.util.List;
import com.google.gson.annotations.Expose;


public class FetchedVideo {

    @Expose
    private Long id;
    @Expose
    private List<Video> results;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Video> getVideos() {
        return results;
    }

    public void setVideos(List<Video> videos) {
        this.results = videos;
    }

}
