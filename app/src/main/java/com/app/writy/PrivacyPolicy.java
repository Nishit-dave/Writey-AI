package com.app.writy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.app.writy.databinding.ActivityPrivacyPolicyBinding;

public class PrivacyPolicy extends AppCompatActivity {

    ActivityPrivacyPolicyBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPrivacyPolicyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.back.setOnClickListener(view -> {
            onBackPressed();
        });


        binding.web.loadUrl("file:///android_asset/privacy_policy.html");
        binding.web.setVerticalScrollBarEnabled(true);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(PrivacyPolicy.this,MainActivity.class));
        finish();
    }
}