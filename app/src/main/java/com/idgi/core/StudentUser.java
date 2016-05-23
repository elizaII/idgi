package com.idgi.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.idgi.Application;
import com.idgi.IBusEvent;

import java.util.ArrayList;
import java.util.List;

@JsonTypeName("student")
public class StudentUser extends User{

    private List<Hat> hats = new ArrayList<>();

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
        postPointUpdate();
    }

    public void givePointsForViewingVideo(Video video, int points) {
        statistics.addVideoPoints(video, points);
        postPointUpdate();
    }

    private void postPointUpdate() {
        Application.getEventBus().post(pointsUpdatedEvent);
    }

    @JsonIgnore
    public int getPoints() {
        return statistics.get(Statistics.Property.POINTS);
    }

    public List<Hat> getHats() {
        return this.hats;
    }

    public void giveHats(List<Hat> hats) {
        for (Hat hat : hats)
            if (!this.hats.contains(hat))
                this.hats.add(hat);
    }

    public boolean containsHat(Hat hat) {
        return hats.contains(hat);
    }

    // TODO Use an actual implementation of BusEvent.
    private IBusEvent pointsUpdatedEvent = new IBusEvent() {
        @Override
        public IBusEvent.Event getEvent() {
            return IBusEvent.Event.POINTS_UPDATED;
        }

        @Override
        public Object getData() {
            return StudentUser.this;
        }
    };
}
