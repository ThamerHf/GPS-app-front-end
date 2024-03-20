package com.akatsuki.gps_app_front.data.model.entity;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Location {
    public Location(String description, Long locationId, String title, String adresse, Double coord) {
        this.description = description;
        this.locationId = locationId;
        this.title = title;
        this.adresse = adresse;
        this.coord = coord;
    }

    private String title;

    private String description;
    private Long locationId;

    private String adresse;

    private Double coord;

    private byte[] image;

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Double getCoord() {
        return coord;
    }

    public void setCoord(Double coord) {
        this.coord = coord;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
