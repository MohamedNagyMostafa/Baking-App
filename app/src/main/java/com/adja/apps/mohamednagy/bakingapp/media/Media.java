package com.adja.apps.mohamednagy.bakingapp.media;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
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

public class Media extends MediaController{
    private SimpleExoPlayer mSimpleExoPlayer;
    private Builder mBuilder;
    private Context mContext;

    public Media(Builder builder, Context context){
        super(builder.mOnMediaStateChanged);
        mBuilder = builder;
        mContext = context;
        init();
    }

    private void init(){
        // initialize exoPlayer
        prepareExoPlayer();
        // Prepare Media
        prepareMediaPlayer();
    }


    public void play(){
        mSimpleExoPlayer.setPlayWhenReady(true);
    }

    public void release(){
        mSimpleExoPlayer.stop();
        mSimpleExoPlayer.release();
        mSimpleExoPlayer = null;
    }

    private void prepareExoPlayer(){
        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();

        mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector, loadControl);
        mBuilder.mSimpleExoPlayerView.setPlayer(mSimpleExoPlayer);

        if(mBuilder.mOnMediaStateChanged != null)
            mSimpleExoPlayer.addListener(this);
    }

    private void prepareMediaPlayer(){
        String userAgent = Util.getUserAgent(mContext, "Baking");
        MediaSource mediaSource = new ExtractorMediaSource(
                mBuilder.mVideoUri,
                new DefaultDataSourceFactory(mContext, userAgent),
                new DefaultExtractorsFactory(),
                null,
                null
        );
        mSimpleExoPlayer.prepare(mediaSource);
    }

    public static class Builder{
        private OnMediaStateChanged mOnMediaStateChanged;
        private SimpleExoPlayerView mSimpleExoPlayerView;
        private Uri mVideoUri;

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
    }

    public enum State{
        READY, IDLE, ENDED, NO_ACTION
    }

    public interface OnMediaStateChanged{
        void onStateChanged(State state, boolean isPlaying);
    }

}
