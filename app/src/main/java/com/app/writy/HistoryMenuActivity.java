package com.app.writy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.writy.ads.AdMob;
import com.app.writy.ads.OnDismiss;
import com.app.writy.databinding.ActivityHistoryMenuBinding;
import com.app.writy.history.HisroryFacebookDescriptionActivity;
import com.app.writy.history.HistorBBlogActivity;
import com.app.writy.history.HistoryEEmailActivity;
import com.app.writy.history.HistoryEEssayActivity;
import com.app.writy.history.HistoryInstaCaptionActivity;
import com.app.writy.history.HistoryInstaHashtagActivity;
import com.app.writy.history.HistoryLinkDinDescriptionActivity;
import com.app.writy.history.HistoryXTweetActivity;
import com.app.writy.history.HistoryYouTubTitleActivity;
import com.app.writy.history.HistoryYouTubeDescriptionActivity;
import com.app.writy.utils.ID;

import java.util.Objects;

public class HistoryMenuActivity extends AppCompatActivity {

    ActivityHistoryMenuBinding binding;
    String name = "instagram";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
            ID.setString(this, "name", "name",name);

            binding.inside.setImageResource(R.drawable.instid_instagram);
            binding.onlyOne.setVisibility(View.GONE);
            binding.twoButtonLayout.setVisibility(View.VISIBLE);
            binding.oneBtnTwo.setImageResource(R.drawable.caption);
            binding.secondBtnTwo.setImageResource(R.drawable.hashtag);

        });

        binding.facebookIcon.setOnClickListener(view -> {
            name = "facebook";
            ID.setString(this, "name", "name",name);

            binding.inside.setImageResource(R.drawable.instid_facebook);
            binding.onlyOne.setVisibility(View.VISIBLE);
            binding.twoButtonLayout.setVisibility(View.GONE);
            binding.onlyOneBtn.setImageResource(R.drawable.description);

        });

        binding.youtubeIcon.setOnClickListener(view -> {
            name = "youtube";
            ID.setString(this, "name", "name",name);

            binding.inside.setImageResource(R.drawable.instid_youtube);
            binding.onlyOne.setVisibility(View.GONE);
            binding.twoButtonLayout.setVisibility(View.VISIBLE);
            binding.oneBtnTwo.setImageResource(R.drawable.title);
            binding.secondBtnTwo.setImageResource(R.drawable.description);

        });

        binding.xIcon.setOnClickListener(view -> {
            name = "x";
            ID.setString(this, "name", "name",name);

            binding.inside.setImageResource(R.drawable.instid_x);
            binding.onlyOne.setVisibility(View.VISIBLE);
            binding.twoButtonLayout.setVisibility(View.GONE);
            binding.onlyOneBtn.setImageResource(R.drawable.tweet);

        });

        binding.blogIcon.setOnClickListener(view -> {
            name = "blog";
            ID.setString(this, "name", "name",name);

            binding.inside.setImageResource(R.drawable.instid_blog);
            binding.onlyOne.setVisibility(View.VISIBLE);
            binding.twoButtonLayout.setVisibility(View.GONE);
            binding.onlyOneBtn.setImageResource(R.drawable.blog);

        });

        binding.linkedinIcon.setOnClickListener(view -> {
            name = "linkedin";
            ID.setString(this, "name", "name",name);

            binding.inside.setImageResource(R.drawable.instid_linkdine);
            binding.onlyOne.setVisibility(View.VISIBLE);
            binding.twoButtonLayout.setVisibility(View.GONE);
            binding.onlyOneBtn.setImageResource(R.drawable.description);

        });

        binding.essayIcon.setOnClickListener(view -> {
            name = "essay";
            ID.setString(this, "name", "name",name);

            binding.inside.setImageResource(R.drawable.instid_essay);
            binding.onlyOne.setVisibility(View.VISIBLE);
            binding.twoButtonLayout.setVisibility(View.GONE);
            binding.onlyOneBtn.setImageResource(R.drawable.essay);


        });

        binding.emailIcon.setOnClickListener(view -> {
            name = "email";
            ID.setString(this, "name", "name",name);

            binding.inside.setImageResource(R.drawable.instid_email);
            binding.onlyOne.setVisibility(View.VISIBLE);
            binding.twoButtonLayout.setVisibility(View.GONE);
            binding.onlyOneBtn.setImageResource(R.drawable.email);

        });


        binding.oneBtnTwo.setOnClickListener(view -> {
            if (!Objects.equals(name, "instagram")) {
                if (Objects.equals(name, "youtube")) {
                    startActivity(new Intent(HistoryMenuActivity.this, HistoryYouTubTitleActivity.class));
                    finish();
                }
            } else {
                startActivity(new Intent(HistoryMenuActivity.this, HistoryInstaCaptionActivity.class));
                finish();
            }
        });

        binding.secondBtnTwo.setOnClickListener(view -> {
            if (!Objects.equals(name, "instagram")) {
                if (Objects.equals(name, "youtube")) {
                    startActivity(new Intent(HistoryMenuActivity.this, HistoryYouTubeDescriptionActivity.class));
                    finish();
                }
            } else {
                startActivity(new Intent(HistoryMenuActivity.this, HistoryInstaHashtagActivity.class));
                finish();
            }
        });

        binding.onlyOneBtn.setOnClickListener(view -> {
            switch (name) {
                case "facebook":
                    startActivity(new Intent(HistoryMenuActivity.this, HisroryFacebookDescriptionActivity.class));
                    finish();
                    break;
                case "x":
                    startActivity(new Intent(HistoryMenuActivity.this, HistoryXTweetActivity.class));
                    finish();
                    break;
                case "blog":
                    startActivity(new Intent(HistoryMenuActivity.this, HistorBBlogActivity.class));
                    finish();
                    break;
                case "linkedin":
                    startActivity(new Intent(HistoryMenuActivity.this, HistoryLinkDinDescriptionActivity.class));
                    finish();
                    break;
                case "essay":
                    startActivity(new Intent(HistoryMenuActivity.this, HistoryEEssayActivity.class));
                    finish();
                    break;
                case "email":
                    startActivity(new Intent(HistoryMenuActivity.this, HistoryEEmailActivity.class));
                    finish();
                    break;
            }
        });
    }

    private void animate(){
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

    @Override
    public void onBackPressed() {
        new AdMob(new OnDismiss() {
            @Override
            public void onDismiss() {
                startActivity(new Intent(HistoryMenuActivity.this,StartMenuActivity.class));
                finish();
            }
        }).showAds2(HistoryMenuActivity.this, true);
    }
}