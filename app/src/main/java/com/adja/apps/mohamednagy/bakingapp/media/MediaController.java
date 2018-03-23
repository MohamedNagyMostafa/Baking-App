package com.adja.apps.mohamednagy.bakingapp.media;

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

    MediaController(Media.OnMediaStateChanged onMediaStateChanged){
        if(onMediaStateChanged != null)
            mOnMediaStateChanged = onMediaStateChanged;
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
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
}
