package com.warmtel.eventbus.demo2.music_eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.warmtel.eventbus.Event;
import com.warmtel.eventbus.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class MusicPlayerActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mPlayerMusicImg;
    private TextView mCurrentTimeTxt, mTotalTimeTxt;
    private SeekBar mSeekBar;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
    /*
     * mIsPlaying == false; 默认播放
     * mIsPlaying == true; 不播放
     */
    private Boolean mIsPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player_layout);

        mPlayerMusicImg = (ImageView) findViewById(R.id.player_pause_img);
        mCurrentTimeTxt = (TextView) findViewById(R.id.player_current_time_txt);
        mTotalTimeTxt = (TextView) findViewById(R.id.player_total_time_txt);
        mSeekBar = (SeekBar) findViewById(R.id.player_seekbar);
        mPlayerMusicImg.setOnClickListener(this);

        EventBus.getDefault().register(this);

        Intent intent = new Intent(this, MusicService.class);
        startService(intent);
        playerMusic();  //设置 mIsPlaying == false; 默认进入播放
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.player_pause_img:
                playerMusic();
                break;
        }
    }

    public void playerMusic() {
        if (mIsPlaying == false) {
            mPlayerMusicImg.setImageResource(R.drawable.player_pause);
            mIsPlaying = true;
        } else {
            mPlayerMusicImg.setImageResource(R.drawable.player_play);
            mIsPlaying = false;
        }
        EventBus.getDefault().postSticky(new Event.PlayerEvent(mIsPlaying));
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEventBusMainThread(Event.TimeEvent timeEvent) {
        int curentTime = timeEvent.getTimes()[0];
        int totalTime = timeEvent.getTimes()[1];
        mSeekBar.setMax(totalTime);
        mSeekBar.setProgress(curentTime);
        mCurrentTimeTxt.setText(simpleDateFormat.format(new Date(curentTime)));
        mTotalTimeTxt.setText(simpleDateFormat.format(new Date(totalTime)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
