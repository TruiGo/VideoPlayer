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

/**
 * Interface definition of control the player
 *
 * @author fengwx
 */
public interface MediaPlayerControl {

    /**
     * Sets the data source path
     *
     * @param path
     */
    void setVideoPath(String path);

    /**
     * Starts or resumes playback. If playback had previously been paused,
     * playback will continue from where it was paused. If playback had
     * been stopped, or never started before, playback will start at the
     * beginning.
     */
    void start();

    /**
     * Stops playback after playback has been stopped or paused
     */
    void stop();

    /**
     * Pauses playback. Call start() to resume
     */
    void pause();

    /**
     * Releases resources associated with this MediaPlayer object
     */
    void release();

    /**
     * Resets the MediaPlayer to its uninitialized state
     */
    void reset();

    /**
     * Checks whether the MediaPlayer is playing
     *
     * @return
     */
    boolean isPlaying();

    /**
     * Seeks to specified time position
     *
     * @param msec
     */
    void seekTo(int msec);

    /**
     * Gets the current playback position
     *
     * @return
     */
    int getCurrentPosition();

    /**
     * Gets the duration of the file
     *
     * @return
     */
    int getDuration();

    /**
     * Returns the width of the video
     *
     * @return
     */
    int getVideoWidth();

    /**
     * Returns the height of the video
     *
     * @return
     */
    int getVideoHeight();

}
