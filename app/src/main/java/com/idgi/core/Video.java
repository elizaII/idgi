package com.idgi.core;

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
