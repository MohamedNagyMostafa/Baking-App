package com.adja.apps.mohamednagy.bakingapp.media.sys;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import com.adja.apps.mohamednagy.bakingapp.media.Media;

/**
 * Created by Mohamed Nagy on 3/24/2018.
 */

public class AudioFocusSystem {
    public static final int ON_DELAY = 0x001;
    public static final int GAINED = 0x003;
    public static final int FAILED = 0x002;
    public static final int LOSS = 0x004;
    public static final int IDLE = 0x005;

    private int mState;
    private Context mContext;
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener;
    private AudioFocusRequest mAudioFocusRequest;
    private AudioManager mAudioManager;
    private Media mMedia;

    public AudioFocusSystem(@NonNull Context context){
        mContext = context;
        init();
    }

    public AudioFocusSystem(@NonNull Context context, @NonNull Media media) {
        mContext = context;
        mMedia   = media;
        init();
    }

    private void init() {
        mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        mState = IDLE;
    }

    public void run() {
        assert mMedia != null;

        mOnAudioFocusChangeListener = getFocusListener();

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            audioFocusSys21();
        }else{
            audioFocusSys26();
        }
    }

    /**
     * Handle focus audio for android API-21 -> API-25
     */
    private void audioFocusSys21(){
        int res = mAudioManager.requestAudioFocus(getFocusListener(),
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN);

        if(res == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            mState = GAINED;
            mMedia.play();
        }
    }
    /**
     * Handle focus audio for android API-26+
     */
    @TargetApi(Build.VERSION_CODES.O)
    private void audioFocusSys26() {
        mAudioFocusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setAudioAttributes(getAudioAttributes())
                .setAcceptsDelayedFocusGain(true)
                .setWillPauseWhenDucked(true)
                .setOnAudioFocusChangeListener(mOnAudioFocusChangeListener)
                .build();

        assert mAudioManager != null;
        int res = mAudioManager.requestAudioFocus(mAudioFocusRequest);

        synchronized (this){
            switch (res){
                case AudioManager.AUDIOFOCUS_REQUEST_FAILED:
                    mState = FAILED;
                    break;
                case AudioManager.AUDIOFOCUS_REQUEST_GRANTED:
                    mState = GAINED;
                    mMedia.play();
                    break;
                case AudioManager.AUDIOFOCUS_REQUEST_DELAYED:
                    mState = ON_DELAY;
                    break;
            }
        }

    }

    /**
     * Build focus audio listener.
     * @return
     */
    private AudioManager.OnAudioFocusChangeListener getFocusListener() {
        return new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                switch (focusChange) {
                    case AudioManager.AUDIOFOCUS_GAIN:
                        synchronized (this) {
                            mState = GAINED;
                        }
                        mMedia.play();
                        break;
                    case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK:
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    case AudioManager.AUDIOFOCUS_LOSS:
                        synchronized (this) {
                            mState = LOSS;
                        }
                        mMedia.pause();
                        break;
                }
            }
        };
    }


    private AudioAttributes getAudioAttributes(){
        return  new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MOVIE)
                .build();
    }

    public int getState(){
        return mState;
    }

    public void setMedia(Media media){
        mMedia = media;
    }

    public void stop() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            mAudioManager.abandonAudioFocus(getFocusListener());
        }else{
            mAudioManager.abandonAudioFocusRequest(mAudioFocusRequest);
        }
    }
}
