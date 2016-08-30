package com.warmtel.eventbus.demo2.music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;

import java.io.IOException;

public class MusicService extends Service {
    private String mMusicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)+"/jinsha.mp3";
    private MediaPlayer mMediaPlayer;

    interface MusicPlayerService{
        boolean IIsPlayering(); //是否正在播放
        void IPlayerMusic();    //播放暂停音乐
        int ICurrentTime();  //当前播放时间
        int ITotalTime();  //播放总时间
    }

    class MusicPlayerBinder extends Binder implements  MusicPlayerService{

        @Override
        public boolean IIsPlayering() {
            return mMediaPlayer.isPlaying();
        }

        @Override
        public void IPlayerMusic() {
            if(IIsPlayering()){
                mMediaPlayer.pause();
            }else{
                mMediaPlayer.start();
            }

        }

        @Override
        public int ICurrentTime() {
            return mMediaPlayer.getCurrentPosition();
        }

        @Override
        public int ITotalTime() {
            return mMediaPlayer.getDuration();
        }


    }

    public MusicPlayerBinder musicPlayerBinder = new MusicPlayerBinder();


    @Override
    public void onCreate() {
        inintMusic();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return musicPlayerBinder;
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
    }

}
