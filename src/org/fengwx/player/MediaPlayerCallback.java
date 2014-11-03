package org.fengwx.player;

import android.media.MediaPlayer;

public interface MediaPlayerCallback {

    void onPrepared(MediaPlayer mp);

    void onCompletion(MediaPlayer mp);

    void onBufferingUpdate(MediaPlayer mp, int percent);

    void onSeekComplete(MediaPlayer mp);

    void onVideoSizeChanged(MediaPlayer mp, int width, int height);

    boolean onError(MediaPlayer mp, int what, int extra);

    boolean onInfo(MediaPlayer mp, int what, int extra);

}
