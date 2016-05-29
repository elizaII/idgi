package com.idgi.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.idgi.event.ApplicationBus;
import com.idgi.event.BusEvent;
import com.idgi.event.Event;

import java.util.ArrayList;
import java.util.List;

@JsonTypeName("student")
public class Student extends User {

    private List<Hat> hats = new ArrayList<>();

    private Statistics statistics;
    private ArrayList<Course> myCourses;

    private Student() {
    }

    public Student(String name) {
        super(name);
        this.statistics = new Statistics();
        myCourses = new ArrayList<>();
    }

    @JsonIgnore
    public int getStatistics(Statistics.Property property) {
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

    public void addComment() {
        statistics.increment(Statistics.Property.COMMENTS);
    }

    public void givePointsForViewingVideo(Video video, int points) {
        statistics.addVideoPoints(video, points);
        postPointUpdate();
    }

    private void postPointUpdate() {
        BusEvent event = new BusEvent(Event.POINTS_UPDATED, this);
        ApplicationBus.post(event);
    }

    @JsonIgnore
    public int getPoints() {
        return statistics.get(Statistics.Property.POINTS);
    }

    public void addToMyCourses(Course course){
        myCourses.add(course);
        statistics.increment(Statistics.Property.ONGOING_COURSES);
    }

    public void removeFromMyCourses(Course course){
        myCourses.remove(course);
        statistics.decrement(Statistics.Property.ONGOING_COURSES);
    }

    public ArrayList<Course> getMyCourses() {
        if (myCourses == null)
            myCourses = new ArrayList<>();

        return myCourses;
    }

    public List<Hat> getHats() {
        return this.hats;
    }

    public void giveHats(List<Hat> hats) {
        for (Hat hat : hats)
            if (!this.hats.contains(hat))
                this.hats.add(hat);
    }

    public boolean hasCourse(Course course) {
        return getMyCourses().contains(course);
    }

    @Override
    public NameableType getType() {
        return NameableType.STUDENT;
    }

    /** Only for JSON serializing. Do not use. */
    public Statistics getStatistics(){
        return this.statistics;
    }

    /** Only for JSON serializing. Do not use. */
    public void setStatistics(Statistics statistics){
        this.statistics = statistics;
    }

    /** Only for JSON serializing. Do not use. */
    public void setMyCourses(ArrayList<Course> myCourses) {
        this.myCourses = myCourses;
    }

}
