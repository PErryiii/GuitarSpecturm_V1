package com.example.admin.guitarspecturm_v1;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

public class MyPlayer implements MediaPlayer.OnPreparedListener,MediaPlayer.OnErrorListener,MediaPlayer.OnCompletionListener {
    private MediaPlayer myPlayer;
    private boolean hasPrepared;

    private void init(){
        if (null == myPlayer){
            myPlayer = new MediaPlayer();
            myPlayer.setOnPreparedListener(this);
            myPlayer.setOnErrorListener(this);
            myPlayer.setOnCompletionListener(this);
        }
    }

    public void play(Context context){
        Uri uri = Uri.parse("http://nx01.sycdn.kuwo.cn/d5a369d087ce2a0f9f5dd39db2d214ba/5d883b32/resource/n2/36/44/1478416719.mp3");
        hasPrepared = false;
        init();
        try{
            myPlayer.reset();
            myPlayer.setDataSource(context, uri);
            myPlayer.prepareAsync();        //异步准备
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void start(){
        if (null != myPlayer && hasPrepared){
            myPlayer.start();
        }
    }

    public void pause(){
        if (null != myPlayer && hasPrepared){
            myPlayer.pause();
        }
    }

    public void seekTo(int position){
        if (null != myPlayer && hasPrepared){
            myPlayer.seekTo(position);
        }
    }

    public void release(){
        hasPrepared = false;
        myPlayer.stop();
        myPlayer.release();
        myPlayer = null;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        hasPrepared = true; // 准备完成后回调到这里

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        hasPrepared = false;
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        hasPrepared = false;
        return false;
    }
}
