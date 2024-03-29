package com.example.admin.guitarspecturm_v1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btn_openDetail;

    private void initWidget(){
        /*FindWidget*/
        btn_openDetail = (Button)findViewById(R.id.btn_openDetail);
        /*SetOnClickListener*/
        btn_openDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openDetail = new Intent(MainActivity.this, MusicDetailActivity.class);
                startActivity(openDetail);
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidget();
    }
}
