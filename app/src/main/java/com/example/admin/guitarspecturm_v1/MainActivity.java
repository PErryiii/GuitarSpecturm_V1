package com.example.admin.guitarspecturm_v1;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView text;
    private LrcView lrcView;
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView) findViewById(R.id.text);

        String lrcString = "[00:00.10]Hard Love - Usher\n" +
                "[00:00.20]Written by:Bibi Bourelly/Raymond IV\n" +
                "[00:00.30]";
        /*LrcParser lrcParser = new LrcParser();
        List<LrcBean> lrcBeanList = lrcParser.parseStr2Bean(lrcString);
        for (int i = 0; i < lrcBeanList.size(); i++){
            text.setText(text.getText() + "\n" + lrcBeanList.get(i).getLrc());
        }*/

        lrcView = (LrcView) findViewById(R.id.lrcView);
        lrcView.setLrc(lrcString);
        lrcView.setPlayer(player);
        lrcView.init();
    }
}
