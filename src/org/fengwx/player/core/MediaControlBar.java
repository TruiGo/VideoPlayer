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

import android.R;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * MediaControlBar, includes play state image, start time, end time, seekbar and so on.
 *
 * @author fengwx
 */
public class MediaControlBar extends RelativeLayout {

    private static final int PLAY_IMAGE_ID = 0x10000;
    private static final int START_TEXT_ID = 0x10001;
    private static final int END_TEXT_ID = 0x10010;
    private static final int SEEKBAR_ID = 0x10011;

    private ImageView mPlayImage;
    private TextView mStartText, mEndText;
    private SeekBar mSeekBar;

    public MediaControlBar(Context context) {
        super(context);
        init();
    }

    public MediaControlBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MediaControlBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setBackgroundColor(Color.argb(60, 200, 200, 200));

        LayoutParams playImageParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        playImageParams.addRule(ALIGN_PARENT_LEFT);
        playImageParams.addRule(CENTER_VERTICAL);
        playImageParams.setMargins(10, 0, 10, 0);
        mPlayImage = new ImageView(getContext());
        mPlayImage.setId(PLAY_IMAGE_ID);
        mPlayImage.setScaleType(ImageView.ScaleType.FIT_XY);
        mPlayImage.setImageResource(R.drawable.ic_media_play);
        addView(mPlayImage, playImageParams);

        LayoutParams startTextParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        startTextParams.addRule(RIGHT_OF, mPlayImage.getId());
        startTextParams.addRule(CENTER_VERTICAL);
        startTextParams.setMargins(10, 0, 10, 0);
        mStartText = new TextView(getContext());
        mStartText.setId(START_TEXT_ID);
        mStartText.setText("00:00");
        addView(mStartText, startTextParams);

        LayoutParams endTextParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        endTextParams.setMargins(10, 0, 10, 0);
        endTextParams.addRule(ALIGN_PARENT_RIGHT);
        endTextParams.addRule(CENTER_VERTICAL);
        mEndText = new TextView(getContext());
        mEndText.setId(END_TEXT_ID);
        mEndText.setText("00:00");
        addView(mEndText, endTextParams);

        LayoutParams seekBarParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        seekBarParams.addRule(LEFT_OF, mEndText.getId());
        seekBarParams.addRule(RIGHT_OF, mStartText.getId());
        seekBarParams.addRule(CENTER_VERTICAL);
        mSeekBar = new SeekBar(getContext());
        mSeekBar.setId(SEEKBAR_ID);
        mSeekBar.setMax(100);
        addView(mSeekBar, seekBarParams);
    }

    public void setStartText(String text) {
        if (null != text) {
            mStartText.setText(text);
        }
    }

    public void setEndText(String text) {
        if (null != text) {
            mEndText.setText(text);
        }
    }

    public void setProgress(int progress) {
        mSeekBar.setProgress(progress);
    }

    public int getMaxProgress() {
        return mSeekBar.getMax();
    }

    public void setSecondaryProgress(int secondaryProgress) {
        mSeekBar.setSecondaryProgress(secondaryProgress);
    }

    public boolean isSeekBarPressed() {
        return mSeekBar.isPressed();
    }

    public void setOnSeekBarChangeListener(SeekBar.OnSeekBarChangeListener onSeekBarChangeListener) {
        mSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
    }

}
