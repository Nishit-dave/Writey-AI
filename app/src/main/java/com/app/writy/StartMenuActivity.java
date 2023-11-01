package com.app.writy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.writy.databinding.ActivityStartMenuBinding;
import com.app.writy.firebase.User;
import com.app.writy.utils.ID;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.UUID;

public class StartMenuActivity extends AppCompatActivity {
    ActivityStartMenuBinding binding;
    String name = "instagram";
    DatabaseReference db;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseAuthentication();

        binding.back.setOnClickListener(view -> {
            onBackPressed();
        });

        switch (name) {
            case "instagram": {
                name = "instagram";
                ID.setString(this, "name", "name", name);

                binding.inside.setImageResource(R.drawable.instid_instagram);
                binding.onlyOne.setVisibility(View.GONE);
                binding.twoButtonLayout.setVisibility(View.VISIBLE);
                binding.oneBtnTwo.setImageResource(R.drawable.caption);
                binding.secondBtnTwo.setImageResource(R.drawable.hashtag);
                break;
            }
            case "facebook": {
                name = "facebook";
                ID.setString(this, "name", "name", name);

                binding.inside.setImageResource(R.drawable.instid_facebook);
                binding.onlyOne.setVisibility(View.VISIBLE);
                binding.twoButtonLayout.setVisibility(View.GONE);
                binding.onlyOneBtn.setImageResource(R.drawable.description);
                break;
            }
            case "youtube": {
                name = "youtube";
                ID.setString(this, "name", "name", name);

                binding.inside.setImageResource(R.drawable.instid_youtube);
                binding.onlyOne.setVisibility(View.GONE);
                binding.twoButtonLayout.setVisibility(View.VISIBLE);
                binding.oneBtnTwo.setImageResource(R.drawable.title);
                binding.secondBtnTwo.setImageResource(R.drawable.description);
                break;
            }
            case "x": {
                name = "x";
                ID.setString(this, "name", "name", name);

                binding.inside.setImageResource(R.drawable.instid_x);
                binding.onlyOne.setVisibility(View.VISIBLE);
                binding.twoButtonLayout.setVisibility(View.GONE);
                binding.onlyOneBtn.setImageResource(R.drawable.tweet);
                break;
            }
            case "blog": {
                name = "blog";
                ID.setString(this, "name", "name", name);

                binding.inside.setImageResource(R.drawable.instid_blog);
                binding.onlyOne.setVisibility(View.VISIBLE);
                binding.twoButtonLayout.setVisibility(View.GONE);
                binding.onlyOneBtn.setImageResource(R.drawable.blog);
                break;
            }
            case "linkedin": {
                name = "linkedin";
                ID.setString(this, "name", "name", name);

                binding.inside.setImageResource(R.drawable.instid_linkdine);
                binding.onlyOne.setVisibility(View.VISIBLE);
                binding.twoButtonLayout.setVisibility(View.GONE);
                binding.onlyOneBtn.setImageResource(R.drawable.description);
                break;
            }
            case "essay": {
                name = "essay";
                ID.setString(this, "name", "name", name);

                binding.inside.setImageResource(R.drawable.instid_essay);
                binding.onlyOne.setVisibility(View.VISIBLE);
                binding.twoButtonLayout.setVisibility(View.GONE);
                binding.onlyOneBtn.setImageResource(R.drawable.essay);
                break;
            }
            case "email": {
                name = "email";
                ID.setString(this, "name", "name", name);

                binding.inside.setImageResource(R.drawable.instid_email);
                binding.onlyOne.setVisibility(View.VISIBLE);
                binding.twoButtonLayout.setVisibility(View.GONE);
                binding.onlyOneBtn.setImageResource(R.drawable.email);
                break;
            }
        }

        animate();
        binding.instagramIcon.setOnClickListener(view -> {
            name = "instagram";
            ID.setString(this, "name", "name", name);

            binding.inside.setImageResource(R.drawable.instid_instagram);
            binding.onlyOne.setVisibility(View.GONE);
            binding.twoButtonLayout.setVisibility(View.VISIBLE);
            binding.oneBtnTwo.setImageResource(R.drawable.caption);
            binding.secondBtnTwo.setImageResource(R.drawable.hashtag);

        });

        binding.facebookIcon.setOnClickListener(view -> {
            name = "facebook";
            ID.setString(this, "name", "name", name);

            binding.inside.setImageResource(R.drawable.instid_facebook);
            binding.onlyOne.setVisibility(View.VISIBLE);
            binding.twoButtonLayout.setVisibility(View.GONE);
            binding.onlyOneBtn.setImageResource(R.drawable.description);

        });

        binding.youtubeIcon.setOnClickListener(view -> {
            name = "youtube";
            ID.setString(this, "name", "name", name);

            binding.inside.setImageResource(R.drawable.instid_youtube);
            binding.onlyOne.setVisibility(View.GONE);
            binding.twoButtonLayout.setVisibility(View.VISIBLE);
            binding.oneBtnTwo.setImageResource(R.drawable.title);
            binding.secondBtnTwo.setImageResource(R.drawable.description);

        });

        binding.xIcon.setOnClickListener(view -> {
            name = "x";
            ID.setString(this, "name", "name", name);

            binding.inside.setImageResource(R.drawable.instid_x);
            binding.onlyOne.setVisibility(View.VISIBLE);
            binding.twoButtonLayout.setVisibility(View.GONE);
            binding.onlyOneBtn.setImageResource(R.drawable.tweet);

        });

        binding.blogIcon.setOnClickListener(view -> {
            name = "blog";
            ID.setString(this, "name", "name", name);

            binding.inside.setImageResource(R.drawable.instid_blog);
            binding.onlyOne.setVisibility(View.VISIBLE);
            binding.twoButtonLayout.setVisibility(View.GONE);
            binding.onlyOneBtn.setImageResource(R.drawable.blog);

        });

        binding.linkedinIcon.setOnClickListener(view -> {
            name = "linkedin";
            ID.setString(this, "name", "name", name);

            binding.inside.setImageResource(R.drawable.instid_linkdine);
            binding.onlyOne.setVisibility(View.VISIBLE);
            binding.twoButtonLayout.setVisibility(View.GONE);
            binding.onlyOneBtn.setImageResource(R.drawable.description);

        });

        binding.essayIcon.setOnClickListener(view -> {
            name = "essay";
            ID.setString(this, "name", "name", name);

            binding.inside.setImageResource(R.drawable.instid_essay);
            binding.onlyOne.setVisibility(View.VISIBLE);
            binding.twoButtonLayout.setVisibility(View.GONE);
            binding.onlyOneBtn.setImageResource(R.drawable.essay);


        });

        binding.emailIcon.setOnClickListener(view -> {
            name = "email";
            ID.setString(this, "name", "name", name);

            binding.inside.setImageResource(R.drawable.instid_email);
            binding.onlyOne.setVisibility(View.VISIBLE);
            binding.twoButtonLayout.setVisibility(View.GONE);
            binding.onlyOneBtn.setImageResource(R.drawable.email);

        });

        binding.oneBtnTwo.setOnClickListener(view -> {
            if (i == 1) {
                if (isInternetConnection() == true) {
                    if (!Objects.equals(name, "instagram")) {
                        if (Objects.equals(name, "youtube")) {
                            startActivity(new Intent(StartMenuActivity.this, YouTubTitleActivity.class));
                            finish();
                        }
                    } else {
                        startActivity(new Intent(StartMenuActivity.this, InstaCaptionActivity.class));
                        finish();
                    }
                } else {
                    Toast.makeText(this, "please check internet. ", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "try again connection error. ", Toast.LENGTH_LONG).show();
            }
        });
        binding.secondBtnTwo.setOnClickListener(view -> {
            if (i == 1) {
                if (isInternetConnection() == true) {
                    if (!Objects.equals(name, "instagram")) {
                        if (Objects.equals(name, "youtube")) {
                            startActivity(new Intent(StartMenuActivity.this, YouTubeDescriptionActivity.class));
                            finish();
                        }
                    } else {
                        startActivity(new Intent(StartMenuActivity.this, InstaHashtagActivity.class));
                        finish();
                    }
                } else {
                    Toast.makeText(this, "please check internet. ", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "try again connection error. ", Toast.LENGTH_LONG).show();
            }
        });

        binding.onlyOneBtn.setOnClickListener(view -> {
            if (i == 1) {
                if (isInternetConnection() == true) {
                    switch (name) {
                        case "facebook":
                            startActivity(new Intent(StartMenuActivity.this, FacebookDescriptionActivity.class));
                            finish();
                            break;
                        case "x":
                            startActivity(new Intent(StartMenuActivity.this, XTweetActivity.class));
                            finish();
                            break;
                        case "blog":
                            startActivity(new Intent(StartMenuActivity.this, BBlogActivity.class));
                            finish();
                            break;
                        case "linkedin":
                            startActivity(new Intent(StartMenuActivity.this, LinkDinDescriptionActivity.class));
                            finish();
                            break;
                        case "essay":
                            startActivity(new Intent(StartMenuActivity.this, EEssayActivity.class));
                            finish();
                            break;
                        case "email":
                            startActivity(new Intent(StartMenuActivity.this, EEmailActivity.class));
                            finish();
                            break;
                    }
                } else {
                    Toast.makeText(this, "please check internet. ", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "try again connection error. ", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void animate() {
        binding.instagramLayout
                .animate()
                .translationY(binding.instagramLayout.getLayoutParams().height)
                .alpha(1.0f).setDuration(400);

        binding.facebookLayout
                .animate()
                .translationY(binding.facebookLayout.getLayoutParams().height)
                .alpha(1.0f).setDuration(600);

        binding.youtubeLayout
                .animate()
                .translationY(binding.youtubeLayout.getLayoutParams().height)
                .alpha(1.0f).setDuration(800);

        binding.xLayout
                .animate()
                .translationY(binding.xLayout.getLayoutParams().height)
                .alpha(1.0f).setDuration(1000);

        binding.blogLayout
                .animate()
                .translationY(binding.blogLayout.getLayoutParams().height)
                .alpha(1.0f).setDuration(1200);

        binding.linkedinLayout
                .animate()
                .translationY(binding.linkedinLayout.getLayoutParams().height)
                .alpha(1.0f).setDuration(1400);

        binding.essayLayout
                .animate()
                .translationY(binding.essayLayout.getLayoutParams().height)
                .alpha(1.0f).setDuration(1600);

        binding.emailLayout
                .animate()
                .translationY(binding.emailLayout.getLayoutParams().height)
                .alpha(1.0f).setDuration(1800);
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

    public void FirebaseAuthentication() {
        db = FirebaseDatabase.getInstance().getReference();

        String uniquePseudoID = "35" +
                Build.BOARD.length() % 10 +
                Build.BRAND.length() % 10 +
                Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 +
                Build.HOST.length() % 10 +
                Build.ID.length() % 10 +
                Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 +
                Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 +
                Build.TYPE.length() % 10 +
                Build.USER.length() % 10;

        String serial = Build.getRadioVersion();
        String uuid = new UUID(uniquePseudoID.hashCode(), serial.hashCode()).toString();
        String brand = Build.BRAND;
        String modelno = Build.MODEL;
        String version = Build.VERSION.RELEASE;
        @SuppressLint("HardwareIds") String android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        ID.setString(StartMenuActivity.this, "uuid", "uuid", uuid);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference dataReference = firebaseDatabase.getReference(uuid);
        dataReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    i = 1;
                } else {
                    User user = new User(uuid, brand, modelno, version, android_id, 150);
                    db.child(uuid).setValue(user);

                    db.child(uuid).get().addOnCompleteListener(task -> {
                        if (!task.isSuccessful()) {
                            i = 5;
                        } else {
                            i = 1;
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(StartMenuActivity.this, "please check internet...", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(StartMenuActivity.this, MainActivity.class));
        finish();
    }
}