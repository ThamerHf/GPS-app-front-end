package com.akatsuki.gps_app_front.model;

public class Location {
    public Location(String locationName, String description) {
        this.locationName = locationName ;
        this.description = description;
    }

    private String locationName;

    private String description;

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
