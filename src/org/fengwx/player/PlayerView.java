package org.fengwx.player;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.RelativeLayout;

import java.io.IOException;

public final class PlayerView extends SurfaceView implements MediaPlayerControl {

    private SurfaceHolder mSurfaceHolder = null;
    private MediaPlayer mMediaPlayer = null;
    private MediaPlayerCallback mMediaPlayerCallback = null;
    private String mPath;
    private int mW, mH;

    public void setMediaPlayerCallback(MediaPlayerCallback mediaPlayerCallback) {
        mMediaPlayerCallback = mediaPlayerCallback;
    }

    public PlayerView(Context context) {
        super(context);
        init();
    }

    public PlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PlayerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mW = getResources().getDisplayMetrics().widthPixels;
        mH = getResources().getDisplayMetrics().heightPixels;
        getHolder().addCallback(mSHCallback);
        getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        setFocusable(false);
        setFocusableInTouchMode(false);
    }

    private void initVideo() {
        if (mPath == null || mSurfaceHolder == null) {
            return;
        }
        release();
        try {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setOnPreparedListener(mPreparedListener);
            mMediaPlayer.setOnCompletionListener(mCompletionListener);
            mMediaPlayer.setOnErrorListener(mErrorListener);
            mMediaPlayer.setOnInfoListener(mInfoListener);
            mMediaPlayer.setOnVideoSizeChangedListener(mVideoSizeChangedListener);
            mMediaPlayer.setOnBufferingUpdateListener(mBufferingUpdateListener);
            mMediaPlayer.setOnSeekCompleteListener(mSeekCompleteListener);
            mMediaPlayer.setScreenOnWhilePlaying(true);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setDisplay(mSurfaceHolder);
            mMediaPlayer.setDataSource(mPath);
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void scale(int videoW, int videoH) {
        log("scale 1 :videoW:" + videoW + ", videoH:" + videoH + ", w:" + mW + ", h:" + mH);

        float wRatio = (float) videoW / (float) mW;
        float hRatio = (float) videoH / (float) mH;

        float ratio = Math.max(wRatio, hRatio);
        videoW = (int) Math.ceil((float) videoW / ratio);
        videoH = (int) Math.ceil((float) videoH / ratio);
        log("scale 2 :wRatio:" + wRatio + ", hRatio:" + hRatio + ", ratio:" + ratio + ", videoW:" + videoW + ", videoH:" + videoH);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(videoW, videoH);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        setLayoutParams(layoutParams);
    }

    private void full() {
        setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
    }

    private MediaPlayer.OnPreparedListener mPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            if (null != mMediaPlayerCallback) {
                mMediaPlayerCallback.onPrepared(mp);
            }
        }
    };

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            if (null != mMediaPlayerCallback) {
                mMediaPlayerCallback.onCompletion(mp);
            }
        }
    };

    private MediaPlayer.OnErrorListener mErrorListener = new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            if (null != mMediaPlayerCallback) {
                return mMediaPlayerCallback.onError(mp, what, extra);
            }
            return false;
        }
    };

    private MediaPlayer.OnInfoListener mInfoListener = new MediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            if (null != mMediaPlayerCallback) {
                mMediaPlayerCallback.onInfo(mp, what, extra);
            }
            return false;
        }
    };

    private MediaPlayer.OnVideoSizeChangedListener mVideoSizeChangedListener = new MediaPlayer.OnVideoSizeChangedListener() {
        @Override
        public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
            scale(width, height);
            if (null != mMediaPlayerCallback) {
                mMediaPlayerCallback.onVideoSizeChanged(mp, width, height);
            }
        }
    };

    private MediaPlayer.OnBufferingUpdateListener mBufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            if (null != mMediaPlayerCallback) {
                mMediaPlayerCallback.onBufferingUpdate(mp, percent);
            }
        }
    };

    private MediaPlayer.OnSeekCompleteListener mSeekCompleteListener = new MediaPlayer.OnSeekCompleteListener() {
        @Override
        public void onSeekComplete(MediaPlayer mp) {
            if (null != mMediaPlayerCallback) {
                mMediaPlayerCallback.onSeekComplete(mp);
            }
        }
    };


    SurfaceHolder.Callback mSHCallback = new SurfaceHolder.Callback() {
        public void surfaceChanged(SurfaceHolder holder, int format,
                                   int w, int h) {
            log("surfaceChanged");
        }

        public void surfaceCreated(SurfaceHolder holder) {
            log("surfaceCreated");
            mSurfaceHolder = holder;
            initVideo();
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            log("surfaceDestroyed");
            if (mMediaPlayer != null) {
                mMediaPlayer.release();
            }
        }
    };


    @Override
    public void setVideoPath(String path) {
        log("setVideoPath:" + path);
//        if (null != mMediaPlayer) {
        try {
            mPath = path;
            initVideo();
        } catch (Exception e) {
            e.printStackTrace();
            mPath = null;
        }
//        }
    }

    @Override
    public void start() {
        if (null != mMediaPlayer) {
            mMediaPlayer.start();
        }
    }

    @Override
    public void stop() {
        if (null != mMediaPlayer) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public void pause() {
        if (null != mMediaPlayer && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
        }
    }

    @Override
    public void release() {
        if (null != mMediaPlayer) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public void reset() {
        if (null != mMediaPlayer) {
            mMediaPlayer.reset();
        }
    }

    @Override
    public boolean isPlaying() {
        if (null != mMediaPlayer) {
            return mMediaPlayer.isPlaying();
        }
        return false;
    }

    @Override
    public void seekTo(int msec) {
        if (null != mMediaPlayer) {
            mMediaPlayer.seekTo(msec);
        }
    }

    @Override
    public int getCurrentPosition() {
        if (null != mMediaPlayer) {
            return mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    @Override
    public int getDuration() {
        if (null != mMediaPlayer) {
            return mMediaPlayer.getDuration();
        }
        return 0;
    }

    @Override
    public int getVideoWidth() {
        if (null != mMediaPlayer) {
            mMediaPlayer.getVideoWidth();
        }
        return 0;
    }

    @Override
    public int getVideoHeight() {
        if (null != mMediaPlayer) {
            mMediaPlayer.getVideoHeight();
        }
        return 0;
    }

    private void log(String s) {
        Log.d("wenxuan", s);
    }
}
