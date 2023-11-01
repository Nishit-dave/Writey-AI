package com.app.writy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import com.app.writy.databinding.ActivitySplaseScreenBinding;

public class SplaseScreenActivity extends AppCompatActivity {

    ActivitySplaseScreenBinding binding;
    int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplaseScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Animation animation = new AlphaAnimation(0, 1);
        animation.setDuration(SPLASH_DISPLAY_LENGTH);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        binding.gif.startAnimation(animation);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplaseScreenActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        },SPLASH_DISPLAY_LENGTH);

    }
}