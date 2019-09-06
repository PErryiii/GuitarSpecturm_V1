package com.example.admin.guitarspecturm_v1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String lrcString = "[00:00.10]Hard Love - Usher" +
                "[00:00.20]Written by:Bibi Bourelly/Raymond IV" +
                "[00:00.30]";
        LrcParser lrcParser = new LrcParser();
        List<LrcBean> lrcBeanList = new ArrayList<>();
        lrcBeanList = lrcParser.parseStr2Bean(lrcString);
        for (int i = 0; i < lrcBeanList.size(); i++){
            System.out.println(i);
        }
    }
}
