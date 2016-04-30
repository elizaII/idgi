package com.idgi.widgets;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.idgi.util.Config;

/**
 * Use the {@link YoutubeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YoutubeFragment extends YouTubePlayerFragment implements YouTubePlayer.OnInitializedListener{

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
        initialize(Config.YOUTUBE_API_KEY,this);
    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        if(!wasRestored){
            //Video video = Storage.getCurrentVideo();
            //youTubePlayer.cueVideo(video.getUrl());
            youTubePlayer.cueVideo("ZXilG7pH3is");
        } else {
            youTubePlayer.play();
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        String error = youTubeInitializationResult.toString();
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
    }

}
