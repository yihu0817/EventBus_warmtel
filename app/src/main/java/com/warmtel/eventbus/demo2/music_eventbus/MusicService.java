package com.warmtel.eventbus.demo2.music_eventbus;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.IBinder;
import android.os.SystemClock;

import com.warmtel.eventbus.Event;

import java.io.IOException;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class MusicService extends Service {
    private String mMusicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)+"/jinsha.mp3";
    private MediaPlayer mMediaPlayer;
    private boolean isUpdateThreadFalg = true;

    @Subscribe(sticky = true)
    public void doPlayMusicEvent(Event.PlayerEvent playerEvent){
        if(mMediaPlayer!= null && mMediaPlayer.isPlaying()){
            mMediaPlayer.pause();
        }else{
            mMediaPlayer.start();
        }
    }

    @Override
    public void onCreate() {
        inintMusic();
        EventBus.getDefault().register(this);
        new UpdateMusicThread().start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 初始化音乐
     */
    private void inintMusic() {
        if (mMediaPlayer == null)
            mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource("file://" + mMusicDir);
            mMediaPlayer.prepare();

            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mMediaPlayer != null){
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        isUpdateThreadFalg =  false;
        EventBus.getDefault().unregister(this);
    }

    class UpdateMusicThread extends Thread{
        int[] times = new int[2];
        @Override
        public void run() {
            while (isUpdateThreadFalg){
                if(mMediaPlayer != null && mMediaPlayer.isPlaying()){
                    times[0] =  mMediaPlayer.getCurrentPosition();
                    times[1] = mMediaPlayer.getDuration();
                    EventBus.getDefault().post(new Event.TimeEvent(times));
                    SystemClock.sleep(1000);
                }
            }
        }
    }
}
