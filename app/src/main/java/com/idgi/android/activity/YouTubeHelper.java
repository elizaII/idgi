package com.idgi.android.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.idgi.R;

/* Helper class for CreateLessonActivity. Extracted to decrease cyclomatic complexity.*/

public class YouTubeHelper {

    /*
     * Trims a youtube link to get the unique ID of a video
     */
    public static String trimLink(String link) {
        if (link.contains("youtube.com")) {

            //A youtube link usually looks like this
            //https://www.youtube.com/watch?v=aE2drlA8vf8
            //where the unique identifier is after the equal sign
            String[] urlFragments = link.split("=");

            //There are times when it can have this appearance
            //https://www.youtube.com/watch?v=aE2drlA8vf8&feature=youtu.be&t=2m
            //so we need to trim the now "aE2drlA8vf8&feature=youtu.be&t=2m"
            //and retrieve the first part
            if (urlFragments[1].contains("&")) {
                String[] tokens = urlFragments[1].split("&");
                return tokens[0];
            }

            return urlFragments[1];

        } else if (link.contains("youtu.be")) {

            //Similarly for the shortened link
            //https://youtu.be/aE2drlA8vf8
            String[] urlFragments = link.split(".be/");

            //And if it contains more information
            //https://youtu.be/aE2drlA8vf8?t=2m
            if (urlFragments[1].contains("?")) {
                String[] tokens = urlFragments[1].split("\\?");
                return tokens[0];
            }

            return urlFragments[1];
        }

        //Returns an empty string, but it shouldn't
        //because the method isYoutubeLink checks
        //if it's a valid link in beforehand
        return "";
    }

    public static boolean isYoutubeLink(String url) {
        return url.contains("youtube.com") || url.contains("youtu.be");
    }

    public static boolean isYouTubeIntent(Intent intent) {
        String action = intent.getAction();
        String type = intent.getType();

        //Inserting a youtube url by choosing it inside the youtube app
        return Intent.ACTION_SEND.equals(action) && "text/plain".equals(type);
    }

    public static void showLinkErrorDialog(Context context) {
        new AlertDialog.Builder(context).setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(context.getString(R.string.create_lesson_invalid_link_title))
                .setMessage(context.getString(R.string.create_lesson_invalid_link_message))
                .setPositiveButton(context.getString(R.string.ok),null)
                .show();
    }
}
