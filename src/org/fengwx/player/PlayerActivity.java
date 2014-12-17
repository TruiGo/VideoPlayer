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

package org.fengwx.player;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.MediaController;
import org.fengwx.player.core.MediaPlayerCallback;
import org.fengwx.player.core.PlayerView;
import org.fengwx.player.core.SimplePlayerCallback;

/**
 * Test Player Activity
 *
 * @author fengwx
 */
public class PlayerActivity extends Activity {

    private final static String url = "http://pl.youku.com/playlist/m3u8?did=838637d7afb0e0461f19a0dfaf3e00859b191c48&ts=1414723781&ctype=65&token=2367&keyframe=1&sid=241472378118665c8f260&ev=1&type=mp4&oip=2090890182&vid=XNzU4NzU3NzA0&ep=RKtAH4IYFImLpUVE0EyJY7AUHdbwibd6msFUjIfP6TgdjHxCQdKRRNbQ6zbXKXaw";

    private PlayerView mPlayerView;
    private MediaController mMediaController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mPlayerView = (PlayerView) findViewById(R.id.player);
        mMediaController = new MediaController(this);
        mMediaController.setAnchorView(mPlayerView);
        mPlayerView.setMediaPlayerCallback(mediaPlayerCallback);
        mPlayerView.setVideoPath(url);
    }

    private MediaPlayerCallback mediaPlayerCallback = new SimplePlayerCallback() {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            return false;
        }

        @Override
        public void onCompletion(MediaPlayer mp) {
        }

        @Override
        public void onPrepared(MediaPlayer mp) {
            mPlayerView.start();
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            if (mPlayerView.isMediaControllerShown()) {
                mPlayerView.hideMediaController();
            } else {
                mPlayerView.showMediaController();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
