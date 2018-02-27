package com.example.playasarc.proyectoplayas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {


    ImageView sunImage;
    Animation rotateAnimation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        startRotatingImage(sunImage);
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation rotateAnimation) {
                Intent intent = new Intent();
                intent.setClass(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    public void startRotatingImage(View view) {
        sunImage = findViewById(R.id.sol);
        rotateAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.sol_anim);
        sunImage.startAnimation(rotateAnimation);
    }


}

