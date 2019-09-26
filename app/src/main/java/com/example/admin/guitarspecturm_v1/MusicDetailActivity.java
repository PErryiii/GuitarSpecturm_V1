package com.example.admin.guitarspecturm_v1;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MusicDetailActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private SeekBar seekBar_music;
    private Button btn_play;

    private MediaPlayer myPlayer;
    private Timer myTimer;
    private TimerTask myTimerTask;

    private Boolean IsChanging = false;

    /*LrcView*/
    private LrcView lrcView;
    String lrcString = "" +
            "[ti:稻香]" + "\n" +
            "[ar:周杰伦]" + "\n" +
            "[al:魔杰座]" + "\n" +
            "[by:]" + "\n" +
            "[offset:0]" + "\n" +
            "[00:02.54]词：周杰伦 曲：周杰伦" + "\n" +
            "[00:15.59]　　对这个世界如果你有太多的抱怨" + "\n" +
            "[00:18.73]　　跌倒了就不敢继续往前走" + "\n" +
            "[00:21.59]　　为什麽人要这麽的脆弱 堕落";

    private void initWidget(){
        /*FindWidget*/
        seekBar_music = (SeekBar)findViewById(R.id.seekBar_music);
        btn_play = (Button)findViewById(R.id.btn_play);
        lrcView = (LrcView) findViewById(R.id.lrcView);
        /*SetOnClickListener*/
        seekBar_music.setOnSeekBarChangeListener(this);
        /*lrcView.setLrc(lrcString);*/
        lrcView.setLrc2(this, R.raw.musiclrc);
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myPlayer.isPlaying()){
                    myPlayer.pause();
                }
                else {
                    myPlayer.start();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_detail);
        initWidget();
        myPlayer = MediaPlayer.create(this, R.raw.music);
        /*LrcView*/
        lrcView.setPlayer(myPlayer);
        lrcView.init();
        /*Set SeekBar*/
        seekBar_music.setMax(myPlayer.getDuration());
        seekBar_music.setProgress(0);
        /*Timer*/
        myTimer = new Timer();
        myTimerTask =new TimerTask() {
            @Override
            public void run() {
                if(IsChanging==true)//当用户正在拖动进度进度条时不处理进度条的的进度
                    return;
                seekBar_music.setProgress(myPlayer.getCurrentPosition());
            }
        };
        myTimer.schedule(myTimerTask, 0, 10);
    }

    @Override
        public void onBackPressed() {
            super.onBackPressed();
            if(myTimer != null){
                myTimer.cancel();
                myTimer.purge();
                myTimer = null;
            }
            if (myPlayer != null) {
                lrcView.setStopFlag(true);
            myPlayer.stop();
            myPlayer.release();
            myPlayer = null;
        }

    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        IsChanging = true;
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        myPlayer.seekTo(seekBar_music.getProgress());
        IsChanging = false;
    }
}
