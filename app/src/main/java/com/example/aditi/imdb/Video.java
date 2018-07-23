
package com.example.aditi.imdb;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Video {

    @Expose
    private String id;

    @Expose
    private String key;
    @Expose
    private String name;
    @Expose
    private String site;
    @Expose
    private Long size;
    @Expose
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
