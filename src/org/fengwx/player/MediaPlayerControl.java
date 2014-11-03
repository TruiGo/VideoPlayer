package org.fengwx.player;

public interface MediaPlayerControl {

    void setVideoPath(String path);

    void start();

    void stop();

    void pause();

    void release();

    void reset();

    boolean isPlaying();

    void seekTo(int msec);

    int getCurrentPosition();

    int getDuration();

    int getVideoWidth();

    int getVideoHeight();

}
