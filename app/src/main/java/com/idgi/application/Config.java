package com.idgi.application;

import android.os.Build;

public final class Config {

	public enum FirebaseMode {
		ACTIVE, INACTIVE
	}

	/* Firebase */
	public static final FirebaseMode firebaseMode = FirebaseMode.ACTIVE;

	/* YouTube */
	public static final String YOUTUBE_API_KEY = "AIzaSyC0sOK4dltFQ9aThbYRshysc1i35wcdaDU";

	/* Quiz */
	//In milliseconds
	public static final int TIME_TO_DISPLAY_QUIZ_ANSWERS = 3 * 1000;

	/* Points */
	public static final int MAX_POINTS_FOR_VIDEO = 600;
	public static final int POINTS_PER_TICK = 85;

	public static int getMinSdk() {
		return Build.VERSION.SDK_INT;
	}
}