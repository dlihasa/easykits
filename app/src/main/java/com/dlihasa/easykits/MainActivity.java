package com.dlihasa.easykits;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dlihasa.countdown.CountDownView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((CountDownView)findViewById(R.id.countDownView))
                .setCompareTime(System.currentTimeMillis()+2000);
    }
}