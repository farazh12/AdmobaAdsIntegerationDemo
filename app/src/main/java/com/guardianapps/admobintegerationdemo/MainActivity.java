package com.guardianapps.admobintegerationdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AdsHandling.getInstance().initAds(this);


        AdsHandling.getInstance().loadAdaptiveBanner(this, (FrameLayout) findViewById(R.id.banner_layout));
    }
}