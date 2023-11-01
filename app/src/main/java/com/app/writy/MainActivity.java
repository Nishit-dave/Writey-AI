package com.app.writy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.app.writy.databinding.ActivityMainBinding;
import com.app.writy.utils.InAppUpdate;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private Handler handler = new Handler();
    private Runnable runnable;
    int ii = 0;
    boolean isok = false;
    private InAppUpdate inAppUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        inAppUpdate = new InAppUpdate(MainActivity.this);
        inAppUpdate.checkForAppUpdate();

        binding.start.setOnClickListener(view -> {
            binding.manuData.setVisibility(View.GONE);
            handler.removeCallbacks(runnable);
            if (isInternetConnection()) {
                startActivity(new Intent(MainActivity.this, StartMenuActivity.class));
                finish();
            } else {
                Toast.makeText(this, "please check internet. ", Toast.LENGTH_LONG).show();
            }

        });

        binding.history.setOnClickListener(view -> {
            binding.manuData.setVisibility(View.GONE);
            handler.removeCallbacks(runnable);
            startActivity(new Intent(MainActivity.this, HistoryMenuActivity.class));
            finish();
        });

        binding.menu.setOnClickListener(view -> {
            if (isok == false){
                binding.manuData.setVisibility(View.VISIBLE);
                isok = true;
            }else if (isok == true){
                binding.manuData.setVisibility(View.GONE);
                isok = false;
            }

        });

        binding.shareapp.setOnClickListener(v -> {
            binding.manuData.setVisibility(View.GONE);
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Writey AI");
                String shareMessage = "want to make instagram, youtube or any social media content description, caption, hashtag using Writey AI app. thane download app from play store.\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + getPackageName() + "\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose where you want to share"));
            } catch (Exception e) {
            }
        });
        binding.privacy.setOnClickListener(v -> {
            binding.manuData.setVisibility(View.GONE);
                Intent p = new Intent(MainActivity.this, PrivacyPolicy.class);
                startActivity(p);
                finish();
        });
    }

    @Override
    protected void onResume() {
        String[] messages = {"make seo friendly YouTube description", "want to make some ranking blog", "make trendy Instagram hashtags", "make attractive YouTube Title", "how to write perfect email", "make awesome Instagram captions", "made easy Facebook post description", "I wonder how I forgot Twitter or X"};
        runnable = new Runnable() {
            @Override
            public void run() {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.change);
                binding.change.startAnimation(animation);
                binding.change.setText(messages[ii]);
                ii++;
                if (ii == 8) {
                    ii = 0;
                }
                handler.postDelayed(runnable, 3000);
            }
        };
        handler.postDelayed(runnable, 3000);
        super.onResume();
        inAppUpdate.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        inAppUpdate.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    public boolean isInternetConnection() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        inAppUpdate.onActivityResult(requestCode, resultCode);
    }
}