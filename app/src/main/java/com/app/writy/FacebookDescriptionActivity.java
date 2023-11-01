package com.app.writy;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.writy.ads.AdMob;
import com.app.writy.ads.AdsUnit;
import com.app.writy.ads.OnDismiss;
import com.app.writy.api.ChatGpt;
import com.app.writy.databinding.ActivityFacebookDescriptionBinding;
import com.app.writy.firebase.User;
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

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class FacebookDescriptionActivity extends AppCompatActivity {
    ActivityFacebookDescriptionBinding binding;
    int[] are = {R.drawable.f12,R.drawable.f4,R.drawable.f6,R.drawable.f14,R.drawable.f2, R.drawable.f3,R.drawable.f7,R.drawable.f13,R.drawable.f15,R.drawable.f5,R.drawable.f8,R.drawable.f9,R.drawable.f10,R.drawable.f11,R.drawable.f1};
    int[] are2 = {R.drawable.glas, R.drawable.happy,R.drawable.angry,R.drawable.bow,R.drawable.hand,R.drawable.warning,R.drawable.bulbe,R.drawable.detactive,R.drawable.morehappy,R.drawable.stare,R.drawable.sad,R.drawable.hart,R.drawable.suprice,R.drawable.glas,R.drawable.microw};
    ArrayList<String> nameData;
    ArrayList<String> tonData;
    languageAdapter adapter;
    @SuppressLint("StaticFieldLeak")
    public static ImageView flag;
    @SuppressLint("StaticFieldLeak")
    public static TextView lname;
    sizeAdapter adapter2;
    @SuppressLint("StaticFieldLeak")
    public static ImageView emoji;
    @SuppressLint("StaticFieldLeak")
    public static TextView emojiText;
    boolean addEmoji = false;
    Dialog pd;
    User user = new User();
    DatabaseReference db;
    String Query;
    int Length = 500;
    public static String language = "English(U.S.)";
    public static String tone = "Formal";
    private final String category = "facebookdescription";
    String title;
    ProgressDialog pp;
    String data;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFacebookDescriptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AdMob.loadAd2(this);

        pp = new ProgressDialog(FacebookDescriptionActivity.this);
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

        pd = new Dialog(FacebookDescriptionActivity.this);
        pd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(pd.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        pd.setContentView(R.layout.loding_dialog);
        pd.setCancelable(false);
        WindowManager.LayoutParams lp0 = new WindowManager.LayoutParams();
        lp0.copyFrom(pd.getWindow().getAttributes());
        lp0.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp0.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp0.gravity = Gravity.CENTER;
        pd.getWindow().setAttributes(lp0);

        binding.back.setOnClickListener(view -> {
            onBackPressed();
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(ID.getString(FacebookDescriptionActivity.this, "uuid", "uuid", null));
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User post = dataSnapshot.getValue(User.class);
                assert post != null;

                String count = String.valueOf(post.coin);
                binding.coinCount.setText(count);

                if (post.coin == 300) {
                    user.setCoin(300);
                    binding.warningCoin.setVisibility(View.GONE);
                    binding.AdsLayout.setVisibility(View.GONE);
                    binding.GenerateLayout.setVisibility(View.VISIBLE);
                } else if (post.coin == 150) {
                    user.setCoin(150);
                    binding.warningCoin.setVisibility(View.GONE);
                    binding.AdsLayout.setVisibility(View.GONE);
                    binding.GenerateLayout.setVisibility(View.VISIBLE);
                } else if (post.coin == 0){
                    user.setCoin(0);
                    binding.warningCoin.setVisibility(View.VISIBLE);
                    binding.AdsLayout.setVisibility(View.VISIBLE);
                    binding.GenerateLayout.setVisibility(View.GONE);
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

        flag = findViewById(R.id.flag);
        lname = findViewById(R.id.lname);
        emoji = findViewById(R.id.emoji);
        emojiText = findViewById(R.id.emojiText);

        binding.mainEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable editable) {

                int inputText = binding.mainEdt.getText().length();
                binding.mainEdtCount.setText(inputText+"/500");
            }
        });
        binding.addKeywordEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable editable) {

                int inputText = binding.addKeywordEdt.getText().length();
                binding.addEdtCount.setText(inputText+"/100");
            }
        });

        nameData = new ArrayList<>();
        nameData.add("English(U.S.)");
        nameData.add("English(U.K.)");
        nameData.add("English(Australia)");
        nameData.add("English(Canada)");
        nameData.add("Chinese");
        nameData.add("Dutch");
        nameData.add("French");
        nameData.add("German");
        nameData.add("Italian");
        nameData.add("Japanese");
        nameData.add("Korean");
        nameData.add("Polish");
        nameData.add("Portuguese");
        nameData.add("Russian");
        nameData.add("Spanish");

        tonData = new ArrayList<>();
        tonData.add("Formal");
        tonData.add("Friendly");
        tonData.add("Brutal");
        tonData.add("Persuasive");
        tonData.add("Confident");
        tonData.add("Cautionary");
        tonData.add("Inspirational");
        tonData.add("Thoughtful");
        tonData.add("Joyful");
        tonData.add("Exciting");
        tonData.add("Grieved");
        tonData.add("Loving");
        tonData.add("Surprised");
        tonData.add("Informative");
        tonData.add("Expert");

        Dialog dialog = new Dialog(FacebookDescriptionActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.language);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        dialog.getWindow().setAttributes(lp);

        RecyclerView rvv = dialog.findViewById(R.id.rv);
        rvv.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        rvv.setLayoutManager(layoutManager);
        adapter = new languageAdapter(this, are, nameData);
        rvv.setAdapter(adapter);

        binding.dialogLanguage.setOnClickListener(view -> dialog.show());

        Dialog dialog2 = new Dialog(FacebookDescriptionActivity.this);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog2.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        dialog2.setContentView(R.layout.size_ton);
        WindowManager.LayoutParams lp2 = new WindowManager.LayoutParams();
        lp2.copyFrom(dialog2.getWindow().getAttributes());
        lp2.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp2.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp2.gravity = Gravity.BOTTOM;
        dialog2.getWindow().setAttributes(lp2);

        RelativeLayout shotll = dialog2.findViewById(R.id.shotLayout);
        RelativeLayout midiumll = dialog2.findViewById(R.id.mediumLayout);
        RelativeLayout longll = dialog2.findViewById(R.id.longLayout);

        ImageView cand = dialog.findViewById(R.id.cansalD);
        cand.setOnClickListener(view -> {
            dialog.dismiss();
        });
        ImageView can = dialog2.findViewById(R.id.cansalDD);
        can.setOnClickListener(view -> {
            dialog2.dismiss();
        });

        RecyclerView ry = dialog2.findViewById(R.id.RecyclerView);
        ry.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager layoutManager2 = new GridLayoutManager(this, 3);
        ry.setLayoutManager(layoutManager2);
        adapter2 = new sizeAdapter(this, are2, tonData);
        ry.setAdapter(adapter2);

        binding.dialogTonLanth.setOnClickListener(view -> dialog2.show());

        shotll.setOnClickListener(view -> {
            shotll.setBackgroundResource(R.drawable.dialog_item_pr);

            midiumll.setBackgroundResource(R.drawable.edt_main_bg);
            longll.setBackgroundResource(R.drawable.edt_main_bg);
            binding.book.setImageResource(R.drawable.onebook);
            binding.bookText.setText("Short");
            Length = 200;
        });

        midiumll.setOnClickListener(view -> {
            midiumll.setBackgroundResource(R.drawable.dialog_item_pr);

            shotll.setBackgroundResource(R.drawable.edt_main_bg);
            longll.setBackgroundResource(R.drawable.edt_main_bg);
            binding.book.setImageResource(R.drawable.twobook);
            binding.bookText.setText("Medium");
            Length = 500;
        });
        longll.setOnClickListener(view -> {
            longll.setBackgroundResource(R.drawable.dialog_item_pr);

            midiumll.setBackgroundResource(R.drawable.edt_main_bg);
            shotll.setBackgroundResource(R.drawable.edt_main_bg);
            binding.book.setImageResource(R.drawable.threebook);
            binding.bookText.setText("Long");
            Length = 700;
        });

        binding.chake.setOnClickListener(view -> {
            if (!addEmoji){
                binding.chake.setImageResource(R.drawable.emoji_chak);
                addEmoji = true;
            }else {
                binding.chake.setImageResource(R.drawable.chake_un);
                addEmoji = false;
            }
        });

        binding.Generate.setOnClickListener(view -> {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            if (isInternetConnection() == true) {
            if (binding.mainEdt.getText().toString().length() > 0) {
                String Description = binding.mainEdt.getText().toString();
                String Keyword = binding.addKeywordEdt.getText().toString();
                title = Description;

                if (addEmoji) {
                    Query = "Generate Facebook Post Description based on this sentence \"" + Description + "\" use this Keyword \"" + Keyword + "\" write in " + language + " language and use " + tone + " tone in writing. Description size must in " + Length + " words also add Emojis";
                } else {
                    Query = "Generate Facebook Post Description based on this sentence \"" + Description + "\" use this Keyword \"" + Keyword + "\" write in " + language + " language and use " + tone + " tone in writing. Description size must in " + Length + " words";
                }
                callAPI(Query, data);
            }
            }else {
                Toast.makeText(this, "please check internet. ", Toast.LENGTH_LONG).show();
            }
        });

        binding.Ads.setOnClickListener(view -> {
            pp.show();
            MobileAds.initialize(FacebookDescriptionActivity.this, initializationStatus -> {

            });
            AdRequest adRequest = new AdRequest.Builder().build();
            RewardedAd.load(FacebookDescriptionActivity.this, AdsUnit.Reword,
                    adRequest, new RewardedAdLoadCallback() {
                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            pp.dismiss();
                            Toast.makeText(FacebookDescriptionActivity.this, "Please try again...", Toast.LENGTH_SHORT).show();
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
                                    },2000);
                                    db = FirebaseDatabase.getInstance().getReference();
                                    user.setCoin(300);
                                    Map<String, Object> postValues = user.toMap();
                                    db.child(ID.getString(FacebookDescriptionActivity.this, "uuid", "uuid", null)).updateChildren(postValues);
                                }
                            }).showAds(FacebookDescriptionActivity.this, true);
                        }
                    });
        });
    }
    void callAPI(String question,String data) {
        pd.show();

        ChatGpt chatGptApi = new ChatGpt();

        chatGptApi.callApi(question,data, new ChatGpt.ApiCallback() {
            @Override
            public void onSuccess(String response) {
                runOnUiThread(() -> {
                    db = FirebaseDatabase.getInstance().getReference();
                    if (user.getCoin() == 150) {
                        user.setCoin(0);
                        Map<String, Object> postValues = user.toMap();
                        db.child(ID.getString(FacebookDescriptionActivity.this, "uuid", "uuid", null)).updateChildren(postValues);

                        pd.dismiss();
                        Intent i = new Intent(FacebookDescriptionActivity.this, ResultActivity.class);
                        i.putExtra("result", response);
                        i.putExtra("Query",Query);
                        i.putExtra("category",category);
                        i.putExtra("title",title);
                        i.putExtra("save",1);
                        startActivity(i);
                        finish();
                    }
                    else if (user.getCoin() == 300) {
                        user.setCoin(150);
                        Map<String, Object> postValues = user.toMap();
                        db.child(ID.getString(FacebookDescriptionActivity.this, "uuid", "uuid", null)).updateChildren(postValues);

                        pd.dismiss();
                        Intent i = new Intent(FacebookDescriptionActivity.this, ResultActivity.class);
                        i.putExtra("result", response);
                        i.putExtra("Query",Query);
                        i.putExtra("category",category);
                        i.putExtra("title",title);
                        i.putExtra("save",1);
                        startActivity(i);
                        finish();
                    }
                    else {
                        pd.dismiss();
                        Toast.makeText(FacebookDescriptionActivity.this, "something wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(String error) {
                runOnUiThread(() -> {
                    pd.dismiss();
                    Toast.makeText(FacebookDescriptionActivity.this, "Try Again...", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
    public static class languageAdapter extends RecyclerView.Adapter<languageAdapter.MyViewHolder> {
        private final Context context;
        int[] are;
        ArrayList<String> nameData;
        int num = 0;

        public languageAdapter(Context context, int[] are, ArrayList<String> nameData) {
            this.context = context;
            this.are = are;
            this.nameData = nameData;

        }

        @NonNull
        @Override
        public languageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.recycal_language, parent, false);
            return new languageAdapter.MyViewHolder(view);
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onBindViewHolder(@NonNull languageAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

            holder.im.setImageResource(are[position]);
            holder.textView.setText(nameData.get(position));

            if (num == position){
                holder.image.setImageResource(R.drawable.sellact_un);
            }else {
                holder.image.setImageResource(R.drawable.not_selact);
            }

            holder.chke_Rela.setOnClickListener(view -> {
                num = position;
                notifyDataSetChanged();
                language = nameData.get(position);
                flag.setImageResource(are[position]);
                lname.setText(nameData.get(position));
            });
        }

        @Override
        public int getItemCount() {
            return this.are.length;
        }

        public static class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView im,image;
            TextView textView;
            RelativeLayout chke_Rela;
            public MyViewHolder(View view) {
                super(view);
                image = view.findViewById(R.id.image);
                im = view.findViewById(R.id.im);
                textView = view.findViewById(R.id.text);
                chke_Rela = view.findViewById(R.id.chke_Rela);
            }
        }
    }

    public static class sizeAdapter extends RecyclerView.Adapter<sizeAdapter.MyViewHolder> {
        private final Context context;
        int[] are;
        ArrayList<String> nameData;
        int num = 0;

        public sizeAdapter(Context context, int[] are, ArrayList<String> nameData) {
            this.context = context;
            this.are = are;
            this.nameData = nameData;

        }

        @NonNull
        @Override
        public sizeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.recycal_size, parent, false);
            return new sizeAdapter.MyViewHolder(view);
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onBindViewHolder(@NonNull sizeAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

            holder.im.setImageResource(are[position]);
            holder.textView.setText(nameData.get(position));

            if (num == position){
                holder.chke_Rela.setBackgroundResource(R.drawable.dialog_item_pr);
            }else {
                holder.chke_Rela.setBackgroundResource(R.drawable.edt_main_bg);
            }

            holder.chke_Rela.setOnClickListener(view -> {
                num = position;
                notifyDataSetChanged();
                tone = nameData.get(position);
                emoji.setImageResource(are[position]);
                emojiText.setText(nameData.get(position));
            });
        }

        @Override
        public int getItemCount() {
            return this.are.length;
        }

        public static class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView im;
            TextView textView;
            RelativeLayout chke_Rela;
            public MyViewHolder(View view) {
                super(view);
                im = view.findViewById(R.id.im);
                textView = view.findViewById(R.id.text);
                chke_Rela = view.findViewById(R.id.chke_Rela);
            }
        }
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
    public void onBackPressed() {
        new AdMob(new OnDismiss() {
            @Override
            public void onDismiss() {
                startActivity(new Intent(FacebookDescriptionActivity.this,StartMenuActivity.class));
                finish();
            }
        }).showAds2(FacebookDescriptionActivity.this, true);
    }
}