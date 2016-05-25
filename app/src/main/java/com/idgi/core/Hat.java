package com.idgi.core;

/*
Hats are used as reward upon reaching certain point milestones.
 */
public class Hat implements Nameable {

    private int imageId;
    private String name;
    private int points;
    private String description;
    private String key;

    public Hat () {

    }

    public NameableType getType() {
        return NameableType.HAT;
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

    /** Auxilliary method, equivalent to getPoints */
    public int pointRequirement() {
        return getPoints();
    }
}
