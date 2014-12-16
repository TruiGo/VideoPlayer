/*
 * Copyright (C) 2014 fengwx.cn@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.fengwx.player.core;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.RelativeLayout;
import org.fengwx.player.util.Logger;

import java.io.IOException;

public final class PlayerView extends SurfaceView implements MediaPlayerControl, MediaPlayerScreen {

    private SurfaceHolder mSurfaceHolder = null;
    private MediaPlayer mMediaPlayer = null;
    private MediaPlayerCallback mMediaPlayerCallback = null;
    private String mPath;
    private int mW, mH;
    private int mVideoW, mVideoH;

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
        Logger.d("player init");
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
        Logger.d("player initVideo");
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
            mVideoW = width;
            mVideoH = height;
            equal_ratio();
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
            Logger.d("surfaceChanged");
        }

        public void surfaceCreated(SurfaceHolder holder) {
            Logger.d("surfaceCreated");
            mSurfaceHolder = holder;
            initVideo();
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            Logger.d("surfaceDestroyed");
            if (mMediaPlayer != null) {
                mMediaPlayer.release();
            }
        }
    };


    @Override
    public void setVideoPath(String path) {
        Logger.d("setVideoPath:" + path);
        mPath = path;
        initVideo();
    }

    @Override
    public void start() {
        if (null != mMediaPlayer) {
            Logger.d("start");
            mMediaPlayer.start();
        }
    }

    @Override
    public void stop() {
        if (null != mMediaPlayer) {
            Logger.d("stop");
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public void pause() {
        if (null != mMediaPlayer && mMediaPlayer.isPlaying()) {
            Logger.d("pause");
            mMediaPlayer.pause();
        }
    }

    @Override
    public void release() {
        if (null != mMediaPlayer) {
            Logger.d("release");
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public void reset() {
        if (null != mMediaPlayer) {
            Logger.d("reset");
            mMediaPlayer.reset();
        }
    }

    @Override
    public boolean isPlaying() {
        if (null != mMediaPlayer) {
            Logger.d("isPlaying:" + mMediaPlayer.isPlaying());
            return mMediaPlayer.isPlaying();
        }
        return false;
    }

    @Override
    public void seekTo(int msec) {
        if (null != mMediaPlayer) {
            Logger.d("seekTo:" + msec);
            mMediaPlayer.seekTo(msec);
        }
    }

    @Override
    public int getCurrentPosition() {
        if (null != mMediaPlayer) {
            Logger.d("getCurrentPosition:" + mMediaPlayer.getCurrentPosition());
            return mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    @Override
    public int getDuration() {
        if (null != mMediaPlayer) {
            Logger.d("getDuration:" + mMediaPlayer.getDuration());
            return mMediaPlayer.getDuration();
        }
        return 0;
    }

    @Override
    public int getVideoWidth() {
        if (null != mMediaPlayer) {
            Logger.d("getVideoWidth:" + mMediaPlayer.getVideoWidth());
            mMediaPlayer.getVideoWidth();
        }
        return 0;
    }

    @Override
    public int getVideoHeight() {
        if (null != mMediaPlayer) {
            Logger.d("getVideoHeight:" + mMediaPlayer.getVideoHeight());
            mMediaPlayer.getVideoHeight();
        }
        return 0;
    }

    @Override
    public void equal_ratio() {
        if (mVideoW == 0 || mVideoH == 0) {
            return;
        }
        Logger.d("equal_ratio");
        float wRatio = (float) mVideoW / (float) mW;
        float hRatio = (float) mVideoH / (float) mH;
        float ratio = Math.max(wRatio, hRatio);
        int w = (int) Math.ceil((float) mVideoW / ratio);
        int h = (int) Math.ceil((float) mVideoH / ratio);
        setLayoutParams(w, h);
    }

    @Override
    public void full() {
        Logger.d("full");
        setLayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void scale_4_3() {
        Logger.d("scale_4_3");
        scale((float) 4 / 3);
    }

    @Override
    public void scale_16_9() {
        Logger.d("scale_16_9");
        scale((float) 16 / 9);
    }

    /**
     * Scale the size of the screen
     *
     * @param r
     */
    private final void scale(float r) {
        if (mW == 0 || mH == 0) {
            return;
        }
        float ratio = (float) mW / (float) mH;
        int w = mW;
        int h = mH;
        if (ratio >= r) {
            w = (int) (h * r);
        } else if (ratio < r) {
            h = (int) (w / r);
        }
        setLayoutParams(w, h);
    }

    /**
     * Set LayoutParams
     *
     * @param w
     * @param h
     */
    private final void setLayoutParams(int w, int h) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(w, h);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        setLayoutParams(layoutParams);
    }

}
