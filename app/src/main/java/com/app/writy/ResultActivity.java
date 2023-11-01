package com.app.writy;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.app.writy.ads.AdMob;
import com.app.writy.ads.AdsUnit;
import com.app.writy.ads.OnDismiss;
import com.app.writy.api.ChatGpt;
import com.app.writy.databinding.ActivityResultBinding;
import com.app.writy.firebase.User;
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
import com.app.writy.roomdb.AppDatabase;
import com.app.writy.roomdb.RoomUser;
import com.app.writy.utils.ID;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.Objects;

public class ResultActivity extends AppCompatActivity {

    ActivityResultBinding binding;
    Dialog lodig;
    User user = new User();
    DatabaseReference db;
    String Query;
    PopupWindow pwindow;
    int save;
    String title;
    String category;
    ProgressDialog pp;
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AdMob.loadAd2(this);

        pp = new ProgressDialog(ResultActivity.this);
        pp.setCancelable(false);
        pp.setMessage("ADS Loading...");

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdView mAdView;
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest1 = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest1);

        binding.back.setOnClickListener(view -> {
            onBackPressed();
        });

        lodig = new Dialog(ResultActivity.this);
        lodig.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(lodig.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        lodig.setContentView(R.layout.loding_dialog);
        lodig.setCancelable(false);
        WindowManager.LayoutParams lp0 = new WindowManager.LayoutParams();
        lp0.copyFrom(lodig.getWindow().getAttributes());
        lp0.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp0.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp0.gravity = Gravity.CENTER;
        lodig.getWindow().setAttributes(lp0);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(ID.getString(ResultActivity.this, "uuid", "uuid", null));
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User post = dataSnapshot.getValue(User.class);

                assert post != null;
                if (post.coin == 300) {
                    user.setCoin(300);
                    binding.Ads.setVisibility(View.GONE);
                    binding.rewrite.setVisibility(View.VISIBLE);
                    binding.textChange.setText("re generate same query again ?");
                } else if (post.coin == 150) {
                    user.setCoin(150);
                    binding.Ads.setVisibility(View.GONE);
                    binding.rewrite.setVisibility(View.VISIBLE);
                    binding.textChange.setText("re generate same query again ?");
                } else if (post.coin == 0){
                    user.setCoin(0);
                    binding.Ads.setVisibility(View.VISIBLE);
                    binding.rewrite.setVisibility(View.GONE);
                    binding.textChange.setText("need 300 coins to re generate");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        DatabaseReference ref2 = database2.getReference("api");
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                data = String.valueOf(dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        Intent i = getIntent();
        String result = i.getStringExtra("result");
        Query = i.getStringExtra("Query");
        title = i.getStringExtra("title");
        category = i.getStringExtra("category");
        save = i.getIntExtra("save", 0);

        binding.result.setText(result);

        binding.copy.setOnClickListener(v1 -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Copied Text", binding.result.getText().toString()+ "\n\nThis result generate using Writey AI a social media content maker app. download app using below play store link \n" + "https://play.google.com/store/apps/details?id=" + getPackageName() + "\n");
            clipboard.setPrimaryClip(clip);
            Toast.makeText(ResultActivity.this, "Text copied", Toast.LENGTH_SHORT).show();
        });

        binding.shar.setOnClickListener(v2 -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
            String shareMessage = binding.result.getText().toString() + "\n\nThis result generate using Writey AI a social media content maker app. download app using below play store link \n" + "https://play.google.com/store/apps/details?id=" + getPackageName() + "\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose where you want to share"));
        });

        binding.back.setOnClickListener(view -> {
            onBackPressed();
        });

        binding.rewrite.setOnClickListener(view -> {
            if (isInternetConnection() == true) {
                binding.result.setText("");
                callAPI(Query, data);
            }else {
                Toast.makeText(this, "please check internet. ", Toast.LENGTH_LONG).show();
            }
        });

        binding.Ads.setOnClickListener(view -> {
            if (isInternetConnection() == true) {
                pp.show();
                MobileAds.initialize(ResultActivity.this, initializationStatus -> {

                });
                AdRequest adRequest = new AdRequest.Builder().build();
                RewardedAd.load(ResultActivity.this, AdsUnit.Reword,
                        adRequest, new RewardedAdLoadCallback() {
                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                pp.dismiss();
                                Toast.makeText(ResultActivity.this, "Please try again...", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, loadAdError.toString());
                                AdsUnit.rewardedAd = null;
                            }

                            @Override
                            public void onAdLoaded(@NonNull RewardedAd ad) {
                                AdsUnit.rewardedAd = ad;
                                pp.dismiss();
                                new AdMob(new OnDismiss() {
                                    @Override
                                    public void onDismiss() {
                                        binding.coinAni.setVisibility(View.VISIBLE);
                                        binding.animationView.playAnimation();

                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                binding.animationView.pauseAnimation();
                                                binding.coinAni.setVisibility(View.GONE);
                                            }
                                        }, 2000);
                                        db = FirebaseDatabase.getInstance().getReference();
                                        user.setCoin(300);
                                        Map<String, Object> postValues = user.toMap();
                                        db.child(ID.getString(ResultActivity.this, "uuid", "uuid", null)).updateChildren(postValues);
                                    }
                                }).showAds(ResultActivity.this, true);
                            }
                        });
            }
            else {
                Toast.makeText(this, "please check internet. ", Toast.LENGTH_LONG).show();
            }
        });

        if (save == 1) {
            saveAnswer(category, title, Query, binding.result.getText().toString());
        }
    }

    void callAPI(String question,String data) {
        lodig.show();
        ChatGpt chatGptApi = new ChatGpt();

        chatGptApi.callApi(question,data, new ChatGpt.ApiCallback() {
            @Override
            public void onSuccess(String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        db = FirebaseDatabase.getInstance().getReference();
                        if (user.getCoin() == 150) {
                            user.setCoin(0);
                            Map<String, Object> postValues = user.toMap();
                            db.child(ID.getString(ResultActivity.this, "uuid", "uuid", null)).updateChildren(postValues);
                            lodig.dismiss();
                            binding.result.setText(response);
                            saveAnswer(category, title, Query, binding.result.getText().toString());
                        }
                        else if (user.getCoin() == 300) {
                            user.setCoin(150);
                            Map<String, Object> postValues = user.toMap();
                            db.child(ID.getString(ResultActivity.this, "uuid", "uuid", null)).updateChildren(postValues);
                            lodig.dismiss();
                            binding.result.setText(response);
                            saveAnswer(category, title, Query, binding.result.getText().toString());
                        }
                        else {
                            lodig.dismiss();
                            Toast.makeText(ResultActivity.this, "You don't have enough coins", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }

            @Override
            public void onFailure(String error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lodig.dismiss();
                        Toast.makeText(ResultActivity.this, "Try Again...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void saveAnswer(String category, String title, String query, String allData) {
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        RoomUser roomUser = new RoomUser();
        roomUser.category = category;
        roomUser.title = title;
        roomUser.query = query;
        roomUser.allData = allData;
        db.userDao().insertUser(roomUser);
    }

    @Override
    public void onBackPressed() {
        new AdMob(new OnDismiss() {
            @Override
            public void onDismiss() {
                if (category.equals("InstaCaption")) {
                    Intent i = new Intent(ResultActivity.this, HistoryInstaCaptionActivity.class);
                    i.putExtra("save",save);
                    startActivity(i);
                    finish();
                } else if (category.equals("InstaHashtag")) {
                    Intent i = new Intent(ResultActivity.this, HistoryInstaHashtagActivity.class);
                    i.putExtra("save",save);
                    startActivity(i);
                    finish();
                } else if (category.equals("facebookdescription")) {
                    Intent i = new Intent(ResultActivity.this, HisroryFacebookDescriptionActivity.class);
                    i.putExtra("save",save);
                    startActivity(i);
                    finish();
                }else if (category.equals("youtubtitle")) {
                    Intent i = new Intent(ResultActivity.this, HistoryYouTubTitleActivity.class);
                    i.putExtra("save",save);
                    startActivity(i);
                    finish();
                }else if (category.equals("youtubdescription")) {
                    Intent i = new Intent(ResultActivity.this, HistoryYouTubeDescriptionActivity.class);
                    i.putExtra("save",save);
                    startActivity(i);
                    finish();
                }else if (category.equals("XTweet")) {
                    Intent i = new Intent(ResultActivity.this, HistoryXTweetActivity.class);
                    i.putExtra("save",save);
                    startActivity(i);
                    finish();
                }else if (category.equals("BBlog")) {
                    Intent i = new Intent(ResultActivity.this, HistorBBlogActivity.class);
                    i.putExtra("save",save);
                    startActivity(i);
                    finish();
                }else if (category.equals("LinkeDin")) {
                    Intent i = new Intent(ResultActivity.this, HistoryLinkDinDescriptionActivity.class);
                    i.putExtra("save",save);
                    startActivity(i);
                    finish();
                }else if (category.equals("EEssay")) {
                    Intent i = new Intent(ResultActivity.this, HistoryEEssayActivity.class);
                    i.putExtra("save",save);
                    startActivity(i);
                    finish();
                }else if (category.equals("Email")) {
                    Intent i = new Intent(ResultActivity.this, HistoryEEmailActivity.class);
                    i.putExtra("save",save);
                    startActivity(i);
                    finish();
                }else {
                    startActivity(new Intent(ResultActivity.this, MainActivity.class));
                    finish();
                }
            }
        }).showAds2(ResultActivity.this, true);

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
}