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

import android.media.MediaPlayer;

/**
 * Interface definition of a callback to be invoked
 *
 * @author fengwx
 */
public interface MediaPlayerCallback {

    /**
     * Called when the media file is ready for playback.
     *
     * @param mp the MediaPlayer that is ready for playback
     */
    void onPrepared(MediaPlayer mp);

    /**
     * Called when the end of a media source is reached during playback.
     *
     * @param mp the MediaPlayer that reached the end of the file
     */
    void onCompletion(MediaPlayer mp);

    /**
     * Called to update status in buffering a media stream received through
     * progressive HTTP download. The received buffering percentage
     * indicates how much of the content has been buffered or played.
     * For example a buffering update of 80 percent when half the content
     * has already been played indicates that the next 30 percent of the
     * content to play has been buffered.
     *
     * @param mp      the MediaPlayer the update pertains to
     * @param percent the percentage (0-100) of the content
     *                that has been buffered or played thus far
     */
    void onBufferingUpdate(MediaPlayer mp, int percent);

    /**
     * Called to indicate the completion of a seek operation.
     *
     * @param mp the MediaPlayer that issued the seek operation
     */
    void onSeekComplete(MediaPlayer mp);

    /**
     * Called to indicate the video size
     *
     * @param mp     the MediaPlayer associated with this callback
     * @param width  the width of the video
     * @param height the height of the video
     */
    void onVideoSizeChanged(MediaPlayer mp, int width, int height);

    /**
     * Called to indicate an error.
     *
     * @param mp    the MediaPlayer the error pertains to
     * @param what  the type of error that has occurred:
     * @param extra an extra code, specific to the error. Typically
     *              implementation dependant.
     * @return True if the method handled the error, false if it didn't.
     *         Returning false, or not having an OnErrorListener at all, will
     *         cause the OnCompletionListener to be called.
     */
    boolean onError(MediaPlayer mp, int what, int extra);

    /**
     * Called to indicate an info or a warning.
     *
     * @param mp    the MediaPlayer the info pertains to.
     * @param what  the type of info or warning.
     * @param extra an extra code, specific to the info. Typically
     *              implementation dependant.
     * @return True if the method handled the info, false if it didn't.
     *         Returning false, or not having an OnErrorListener at all, will
     *         cause the info to be discarded.
     */
    boolean onInfo(MediaPlayer mp, int what, int extra);

}
