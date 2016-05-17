package com.idgi.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.ArrayList;
import java.util.List;

@JsonTypeName("student")
public class StudentUser extends User{

    List<Hat> hats = new ArrayList<Hat>();

    private Statistics statistics;

    private StudentUser(){super();}

    public StudentUser(String name) {
        super(name);
        this.statistics = new Statistics();
    }

    @JsonIgnore
    public int getStat(Statistics.Property property) {
        return statistics.get(property);
    }

    @JsonIgnore
    public int getPointsForVideo(Video video) {
        return statistics.getVideoPoints(video);
    }

    public void givePointsForQuiz(String quizID, int points) {
        statistics.updateQuizPoints(quizID, points);
    }

    public void givePointsForViewingVideo(Video video, int points) {
        statistics.addVideoPoints(video, points);
    }

    @JsonIgnore
    public int getPoints() {
        return statistics.get(Statistics.Property.POINTS);
    }


    public List<Hat> getHats() {
        return hats;
    }

    public void giveHats() {
        if (getPoints() >= 100) {
       //     addHat();
        }
    }

    public void addHat(Hat hat) {
        if (!containsHat(hat)) {
            hats.add(hat);
        }
    }

    public boolean containsHat(Hat hat) {
        return hats.contains(hat);
    }

}
