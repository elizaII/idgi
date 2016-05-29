package com.idgi.core;

/*
Class representing a video that can be played in a Lesson.
 */
public class Video {

	private final String url;

	private Video() {
		url = "";
	}

	public Video(String url) {
		this.url = url;
	}

	public static Video from(String url) {
		return new Video(url);
	}

	public String getUrl(){
        return url;
    }
}
