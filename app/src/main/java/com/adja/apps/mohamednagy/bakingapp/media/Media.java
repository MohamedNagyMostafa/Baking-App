package com.adja.apps.mohamednagy.bakingapp.media;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import com.adja.apps.mohamednagy.bakingapp.media.sys.AudioFocusSystem;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * Created by Mohamed Nagy on 3/23/2018.
 */

public class Media{
    private Uri mVideoUri;
    private SimpleExoPlayer mSimpleExoPlayer;
    private SimpleExoPlayerView mSimpleMediaView;

    private OnMediaStateChanged mOnMediaStateChanged;
    private MediaSessionController mMediaSessionController;

    private Context mContext;
    private AudioFocusSystem mAudioFocusSystem;

    Media(Context context, SimpleExoPlayerView simpleExoPlayerView, Uri videoUri,
          OnMediaStateChanged onMediaStateChanged, AudioFocusSystem audioFocusSystem){
        mVideoUri            = videoUri;
        mContext             = context;
        mSimpleMediaView     = simpleExoPlayerView;
        mOnMediaStateChanged = onMediaStateChanged;
        mAudioFocusSystem    = audioFocusSystem;
        init();
    }

    private void init(){
        prepareMediaFocus();
        // prepare media session.
        prepareMediaSession();
        // initialize exoPlayer
        prepareExoPlayer();
        // Prepare Media
        prepareMediaPlayer();
    }


    public void play(){
        if(mAudioFocusSystem.getState() != AudioFocusSystem.GAINED)
            mAudioFocusSystem.run();
        mSimpleExoPlayer.setPlayWhenReady(true);
    }

    public void pause(){
        mSimpleExoPlayer.setPlayWhenReady(false);
    }

    public void release(){
        mMediaSessionController.stop();

        if(mSimpleExoPlayer != null) {

            mSimpleExoPlayer.stop();
            mSimpleExoPlayer.release();
            mSimpleExoPlayer = null;

        }

        if(mAudioFocusSystem != null)
            mAudioFocusSystem.stop();
    }

    /**
     * Attach MediaFocusSystem to media
     */
    private void prepareMediaFocus(){
        mAudioFocusSystem.setMedia(this);
    }

    /**
     * Initialize Original media and set listener for its states.
     * (Connect player state changing with session player and set outer listener "OnMediaStateChanged"
     * to handle the changes by UI)
     */
    private void prepareExoPlayer(){
        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();

        mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector, loadControl);
        mSimpleMediaView.setPlayer(mSimpleExoPlayer);

        if(mOnMediaStateChanged != null)
            mSimpleExoPlayer.addListener(mMediaSessionController);
    }

    /**
     * Connect media session with original media
     * (Connect media session actions with original media and update media changing state with session state)
     */
    private void prepareMediaSession(){
        mMediaSessionController = new MediaSessionController(mContext, mOnMediaStateChanged,
                new MediaSessionController.MediaSessionControllerListener() {
            @Override
            public void updateSessionState(PlaybackStateCompat.Builder playbackStateCompat, int state) {
                playbackStateCompat.setState(state, mSimpleExoPlayer.getCurrentPosition(), 1f);
            }

            @Override
            public void onStateChanged(boolean isPlaying) {
                mSimpleExoPlayer.setPlayWhenReady(isPlaying);
                Log.e("session changed","aaaaaaaaaaaaaaaaaaaaaaaaaaa");


            }
        });
    }

    private void prepareMediaPlayer(){
        String userAgent = Util.getUserAgent(mContext, "Baking");
        MediaSource mediaSource = new ExtractorMediaSource(
                mVideoUri,
                new DefaultDataSourceFactory(mContext, userAgent),
                new DefaultExtractorsFactory(),
                null,
                null
        );
        mSimpleExoPlayer.prepare(mediaSource);
        mMediaSessionController.start();
    }

    public static class Builder{
        private OnMediaStateChanged mOnMediaStateChanged;
        private SimpleExoPlayerView mSimpleExoPlayerView;
        private AudioFocusSystem mAudioFocusSystem;
        private Context mContext;
        private Uri mVideoUri;

        public Builder(Context context){
            mContext = context;
        }

        public Builder mediaView(@NonNull SimpleExoPlayerView simpleExoPlayerView){
            mSimpleExoPlayerView = simpleExoPlayerView;
            return this;
        }

        public Builder defaultImage(@NonNull Bitmap bitmapImage){
            assert mSimpleExoPlayerView != null;
            mSimpleExoPlayerView.setDefaultArtwork(bitmapImage);
            return this;
        }

        public Builder videoLink(@NonNull String videoLink){
            mVideoUri = Uri.parse(videoLink);
            return this;
        }

        public Builder mediaStateListener(@NonNull OnMediaStateChanged onMediaStateChanged){
            mOnMediaStateChanged = onMediaStateChanged;
            return this;
        }

        public Builder audioFocusSystem(@Nullable AudioFocusSystem audioFocusSystem){
            if(audioFocusSystem != null)
                mAudioFocusSystem = audioFocusSystem;

            return this;
        }

        public Media build(){
            return new Media(mContext, mSimpleExoPlayerView, mVideoUri, mOnMediaStateChanged, mAudioFocusSystem);
        }
    }

    public enum State{
        READY, IDLE, ENDED, NO_ACTION
    }

    public interface OnMediaStateChanged{
        void onStateChanged(State state, boolean isPlaying);
    }

}
