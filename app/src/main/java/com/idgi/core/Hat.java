package com.idgi.core;

public class Hat implements Nameable {

    private int imageId;
    private String name;
    private int points;
    private String description;
    private String key;

    public Hat () {

    }

    public Type getType() {
        return Type.HAT;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int pointRequirement() {
        return getPoints();
    }
}
