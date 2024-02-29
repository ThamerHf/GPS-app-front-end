package com.akatsuki.gps_app_front.model;

public class Collection {

    public Collection() {
    }

    public Collection(String tag, Long elemCount) {
        this.tag = tag;
        ElemCount = elemCount;
    }

    private String tag;

    private Long ElemCount;

    public Long getElemCount() {
        return ElemCount;
    }

    public void setElemCount(Long elemCount) {
        ElemCount = elemCount;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
