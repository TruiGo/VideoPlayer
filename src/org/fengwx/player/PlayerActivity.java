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
import android.view.View;
import org.fengwx.player.core.PlayerView;
import org.fengwx.player.core.SimplePlayerCallback;
import org.fengwx.player.util.Logger;

/**
 * Test Player Activity
 *
 * @author fengwx
 */
public class PlayerActivity extends Activity {

    private final static String url = "http://112.25.63.133/gitv_live/CCTV-2/CCTV-2.m3u8?p=CMNET_JIANGSU";
//    private final static String url = "http://pl.youku.com/playlist/m3u8?did=838637d7afb0e0461f19a0dfaf3e00859b191c48&ts=1414723781&ctype=65&token=2367&keyframe=1&sid=241472378118665c8f260&ev=1&type=mp4&oip=2090890182&vid=XNzU4NzU3NzA0&ep=RKtAH4IYFImLpUVE0EyJY7AUHdbwibd6msFUjIfP6TgdjHxCQdKRRNbQ6zbXKXaw";

    private PlayerView mPlayerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mPlayerView = (PlayerView) findViewById(R.id.player);
        mPlayerView.setMediaPlayerCallback(new SimplePlayerCallback() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Logger.d("onPrepared");
                mPlayerView.start();
            }

            @Override
            public void onCompletion(MediaPlayer mp) {
                Logger.d("onCompletion");
            }

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Logger.e("onError: " + what + "  " + extra);
                return false;
            }

        });
        mPlayerView.setVideoPath(url);
    }

    public void btn1(View view) {
        mPlayerView.full();
    }

    public void btn2(View view) {
        mPlayerView.equal_ratio();
    }

    public void btn3(View view) {
        mPlayerView.scale_4_3();
    }

    public void btn4(View view) {
        mPlayerView.scale_16_9();
    }

}
