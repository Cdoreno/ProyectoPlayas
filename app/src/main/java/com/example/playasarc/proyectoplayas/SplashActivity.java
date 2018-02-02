package com.example.playasarc.proyectoplayas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {


    ImageView rotateImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        startRotatingImage(rotateImage);
    }

    public void startRotatingImage(View view) {
        rotateImage = findViewById(R.id.sol);
        Animation startRotateAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.sol_anim);
        rotateImage.startAnimation(startRotateAnimation);
    }
}