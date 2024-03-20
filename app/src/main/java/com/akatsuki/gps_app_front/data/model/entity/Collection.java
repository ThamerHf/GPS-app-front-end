package com.akatsuki.gps_app_front.data.model.entity;

public class Collection {

    public Collection() {
    }

    public Collection(String tag, Long elemCount) {
        this.tag = tag;
        numberOfLocations = elemCount;
    }

    private String tag;

    private Long numberOfLocations;

    public Long getNumberOfLocations() {
        return numberOfLocations;
    }

    public void setNumberOfLocations(Long numberOfLocations) {
        this.numberOfLocations = numberOfLocations;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
