package com.idgi.android.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.idgi.core.StudentUser;
import com.idgi.core.Video;
import com.idgi.Config;
import com.idgi.session.SessionData;

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
            Video currentVideo = SessionData.getCurrentVideo();
            if(currentVideo != null) {
                youTubePlayer.cueVideo(currentVideo.getUrl());
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
            Toast.makeText(getActivity().getBaseContext(), "Playing!", Toast.LENGTH_SHORT).show();
            videoPaused = false;
        }

        @Override
        public void onPaused() {
            //Toast.makeText(getActivity().getBaseContext(), "Pausing.", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getActivity().getBaseContext(), "Video ended.", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };

    private void awardPoints(int points) {
		if (SessionData.hasLoggedInUser() && (SessionData.getLoggedInUser() instanceof StudentUser)) {
			Toast.makeText(getActivity().getBaseContext(), "Points for you!", Toast.LENGTH_SHORT).show();
            ((StudentUser) SessionData.getLoggedInUser()).
                    givePointsForViewingVideo(SessionData.getCurrentVideo(), points);
			updatePointProgressBar();
		}
    }

    private void updateTimeSpentWatching() {
		if (SessionData.hasLoggedInUser()) {
			milliSecondsSpentWatching += 2000;
			if (milliSecondsSpentWatching % TIME_TO_GET_POINTS == 0)
				awardPoints(Config.POINTS_PER_TICK);
		}
    }

    public interface FragmentListener {
        void onUpdatePoints(int value);
    }

    private void updatePointProgressBar() {
		int points = ((StudentUser) SessionData.getLoggedInUser()).getPointsForVideo(SessionData.getCurrentVideo());
        listener.onUpdatePoints(points);
    }
}
