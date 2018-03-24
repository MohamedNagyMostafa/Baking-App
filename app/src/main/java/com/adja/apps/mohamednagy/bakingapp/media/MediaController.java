package com.adja.apps.mohamednagy.bakingapp.media;

import android.media.session.MediaSession;
import android.util.Log;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;

/**
 * Created by Mohamed Nagy on 3/23/2018.
 */

public abstract class MediaController implements ExoPlayer.EventListener {

    private Media.OnMediaStateChanged mOnMediaStateChanged;
    private MediaControllerListener mMediaControllerListener;

    MediaController(Media.OnMediaStateChanged onMediaStateChanged){
        if(onMediaStateChanged != null)
            mOnMediaStateChanged = onMediaStateChanged;

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        Log.e("state changed","aaaaaaaaaaaaaaaaaaaaaaaaaaa");
        handleMediaListener(playWhenReady, playbackState);
        handleSessionListener(playWhenReady, playbackState);

    }

    private void handleMediaListener(boolean playWhenReady, int playbackState){
        assert mOnMediaStateChanged != null;

        Media.State state = Media.State.NO_ACTION;
        switch (playbackState){
            case ExoPlayer.STATE_ENDED:
                state = Media.State.ENDED;
                break;
            case ExoPlayer.STATE_IDLE:
                state = Media.State.IDLE;
                break;
            case ExoPlayer.STATE_READY:
                state = Media.State.READY;
                break;
        }

        mOnMediaStateChanged.onStateChanged(state, playWhenReady);
    }

    /**
     * Send changing state to media session.
     * @param playWhenReady
     * @param playbackState
     */
    private void handleSessionListener(boolean playWhenReady, int playbackState){
        Log.e("session","called");
        if(playbackState == ExoPlayer.STATE_READY){
            if(playWhenReady)
                mMediaControllerListener.onStateChanged(true);
            else
                mMediaControllerListener.onStateChanged(false);
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    protected void setMediaSessionListener(MediaControllerListener
                                                 mediaControllerListener){
        mMediaControllerListener = mediaControllerListener;
    }

    public interface MediaControllerListener {
        void onStateChanged(boolean isPlaying);
    }
}
