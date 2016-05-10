package com.idgi.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.idgi.util.Config;
import com.idgi.util.Storage;

/**
 * Use the {@link YoutubeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YoutubeFragment extends YouTubePlayerFragment implements YouTubePlayer.OnInitializedListener{

    private FragmentListener listener;

    //MUST be multiple of 2000
    private static final int TIME_TO_GET_POINTS = 5 * 1000;

    private Handler handler = new Handler();

    private long milliSecondsSpentWatching = 0;

    private boolean videoPaused = true;

    public YoutubeFragment() {
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        listener = (FragmentListener) context;
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
                youTubePlayer.cueVideo(Storage.getCurrentVideo().getUrl());
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
            //Toast.makeText(getContext(), "Pausing.", Toast.LENGTH_SHORT).show();
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

    private void awardPoints(int points) {
        Toast.makeText(getContext(), "Points for you!", Toast.LENGTH_SHORT).show();
        Storage.getActiveUser().givePointsForViewingVideo(Storage.getCurrentVideo(), points);
        updatePointProgressBar();
    }

    private void updateTimeSpentWatching() {
        milliSecondsSpentWatching += 2000;
        if (milliSecondsSpentWatching % TIME_TO_GET_POINTS == 0)
            awardPoints(Config.POINTS_PER_TICK);
    }

    public interface FragmentListener {
        void updatePoints(int value);
    }

    private void updatePointProgressBar() {
        listener.updatePoints(Storage.getActiveUser().getPointsForVideo(Storage.getCurrentVideo()));
    }
}
