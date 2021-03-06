package com.adja.apps.mohamednagy.bakingapp.media;

import android.content.Context;
import android.media.session.MediaSession;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

/**
 * Created by Mohamed Nagy on 3/23/2018 .
 * Project projects submission
 * Time    10:59 AM
 */

public class MediaSessionController extends MediaController
    implements MediaController.MediaControllerListener {

    private static final String TAG = "MediaSessionController";

    private MediaSession mMediaSession;
    private PlaybackStateCompat.Builder mPlaybackStateCompatBuilder;
    private MediaSessionControllerListener mMediaSessionControllerListener;

    MediaSessionController(Context context,
                           Media.OnMediaStateChanged onMediaStateChanged,
                           MediaSessionControllerListener mediaSessionControllerListner){
        super(onMediaStateChanged);

        setMediaSessionListener(this);

        mMediaSessionControllerListener = mediaSessionControllerListner;
        init(context);
    }

    private void init(Context context){
        MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(context, TAG);
        mediaSessionCompat.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS|
        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSessionCompat.setMediaButtonReceiver(null);

        mPlaybackStateCompatBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE |
                                PlaybackStateCompat.ACTION_PAUSE
                );

        mediaSessionCompat.setPlaybackState(mPlaybackStateCompatBuilder.build());

        mMediaSession = new MediaSession(context, TAG);
        mMediaSession.setCallback(new SessionControllerCallback());
    }

    void start(){
        mMediaSession.setActive(true);
    }

    void stop(){
        mMediaSession.setActive(false);
    }

    // Media Controller Listener.
    @Override
    public void onStateChanged(boolean isPlaying) {
        if(isPlaying)
            mMediaSessionControllerListener.updateSessionState(
                    mPlaybackStateCompatBuilder, PlaybackStateCompat.STATE_PLAYING
            );
        else
            mMediaSessionControllerListener.updateSessionState(
                    mPlaybackStateCompatBuilder, PlaybackStateCompat.STATE_PAUSED
            );
    }

    // Sessions Listener.
    private class SessionControllerCallback extends MediaSession.Callback{
        @Override
        public void onPlay() {
            mMediaSessionControllerListener.onStateChanged(true);
        }

        @Override
        public void onPause() {
            mMediaSessionControllerListener.onStateChanged(false);
        }

    }

    interface MediaSessionControllerListener extends MediaControllerListener {
        void updateSessionState(PlaybackStateCompat.Builder playbackStateCompat, int state);
    }

}
