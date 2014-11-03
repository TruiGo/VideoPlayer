package org.fengwx.player;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;

public class MyActivity extends Activity {

    private static String url = "http://60.206.107.26/sohu/s26h23eab6/ts/zC1LzESlslvesEAg0EwgoEtiTmvEXE1-0avWXw-aOLmvS95Zj9Y5E6xN49rAhWqAH9hAzELuT6QAhlkNspAuoEtNqEP?key=wf9KiTuHqKH5k1K6XR5xUYh3OFYztaW_NB382g..&r=OZ-AtpRgTC-1XfwNXlVLTlvAHdi64FVbtf5Lz3-A4pAGhlibsZoVqpHA&n=6&a=4019&cip=218.241.193.67&nid=464&uid=17ef191f60d0921c690afccc32b8da98&plat=15&pg=1&pt=6&ch=tv&vid=1782554&prod=ott&eye=0##";
//    private final static String url = "http://112.25.63.133/gitv_live/CCTV-2/CCTV-2.m3u8?p=CMNET_JIANGSU";
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
                log("onPrepared");
                mPlayerView.start();
            }

            @Override
            public void onCompletion(MediaPlayer mp) {
                log("onCompletion");
            }

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                log("onError: " + what + "  " + extra);
                return false;
            }

        });
//        String u = Environment.getExternalStorageDirectory()
//                + "/a.mp4";
//        url = u;
        mPlayerView.setVideoPath(url);
    }

    private void log(String s) {
        Log.d("wenxuan", s);
    }

}
