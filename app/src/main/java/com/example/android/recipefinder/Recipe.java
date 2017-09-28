package com.example.android.recipefinder;

import com.google.gson.annotations.SerializedName;

public class Recipe {

    @SerializedName("title")
    private String title;
    @SerializedName("image_url")
    private String image;
    @SerializedName("source_url")
    private String url;
    @SerializedName("social_rank")
    private String ranking;

    public Recipe(String title, String image, String url, String ranking) {
        this.title = title;
        this.image = image;
        this.url = url;
        this.ranking = ranking;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String label) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }
}
