package com.idgi.Widgets;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.BounceInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.viewanimator.ViewAnimator;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.idgi.R;
import com.idgi.core.Video;
import com.idgi.util.Config;
import com.idgi.util.Storage;

import java.util.Locale;
import java.util.Timer;
import java.util.concurrent.RunnableFuture;

/**
 * Use the {@link YoutubeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YoutubeFragment extends YouTubePlayerFragment implements YouTubePlayer.OnInitializedListener{

    //MUST be multiple of 2000
    private static final int TIME_TO_GET_POINTS = 5 * 1000;
    private static final int POINTS_PER_TICK = 350;

    private Handler handler = new Handler();

    private Video video;

    private long milliSecondsSpentWatching = 0;

    private boolean videoPaused = true;

    public YoutubeFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment YoutubeFragment.
     */
    public static YoutubeFragment newInstance() {
        return new YoutubeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize(Config.YOUTUBE_API_KEY, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {


        youTubePlayer.setPlayerStateChangeListener(playStateListener);
        youTubePlayer.setPlaybackEventListener(playbackListener);

        if(!wasRestored){
            if(Storage.getCurrentVideo() != null) {
                video = Storage.getCurrentVideo();
                youTubePlayer.cueVideo(video.getUrl());
            } else {
                //Todo... better error-handling
                youTubePlayer.cueVideo("ZXilG7pH3is");
            }
        } else {
            youTubePlayer.play();
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        String error = youTubeInitializationResult.toString();
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
    }

    private YouTubePlayer.PlaybackEventListener playbackListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {
            Toast.makeText(getContext(), "Playing!", Toast.LENGTH_SHORT).show();
            videoPaused = false;
        }

        @Override
        public void onPaused() {
            Toast.makeText(getContext(), "Pausing.", Toast.LENGTH_SHORT).show();
            videoPaused = true;
        }

        @Override
        public void onStopped() {

        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    };

    private YouTubePlayer.PlayerStateChangeListener playStateListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {
            videoPaused = false;

             Runnable runnable = new Runnable() {
                 public void run() {

                     if (!videoPaused)
                         updateTimeSpentWatching();

                     handler.postDelayed(this, 2000);
                 }
             };
            handler.postDelayed(runnable, 0);
        }

        @Override
        public void onVideoEnded() {
            Toast.makeText(getContext(), "Video ended.", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };

    // TODO
    private void showPointAnimation(int points) {
       /* TextView view = new TextView(getContext());
        view.setText(String.format(Locale.ENGLISH, "+%d", points));


       RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        //RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) root.getLayoutParams();

        layoutParams.addRule(RelativeLayout.BELOW, getView().findViewById(R.id.youtube_view).getId());

        root.addView(view, layoutParams);

        ViewAnimator.animate(view).bounceIn().interpolator(new BounceInterpolator()).start();*/
    }

    private void awardPoints(int points) {
        Toast.makeText(getContext(), "Points for you!", Toast.LENGTH_SHORT).show();
        Storage.getActiveUser().givePointsForViewingVideo(video, points);
        showPointAnimation(points);
    }

    private void updateTimeSpentWatching() {
        milliSecondsSpentWatching += 2000;
        if (milliSecondsSpentWatching % TIME_TO_GET_POINTS == 0)
            awardPoints(POINTS_PER_TICK);
    }
}
