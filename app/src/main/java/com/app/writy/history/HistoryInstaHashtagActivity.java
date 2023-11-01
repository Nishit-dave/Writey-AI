package com.app.writy.history;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.writy.HistoryMenuActivity;
import com.app.writy.R;
import com.app.writy.ResultActivity;
import com.app.writy.StartMenuActivity;
import com.app.writy.ads.AdMob;
import com.app.writy.ads.OnDismiss;
import com.app.writy.databinding.ActivityHistoryInstaHashtagBinding;
import com.app.writy.roomdb.AppDatabase;
import com.app.writy.roomdb.RoomUser;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class HistoryInstaHashtagActivity extends AppCompatActivity {

    ActivityHistoryInstaHashtagBinding binding;
    historyAdapter adapter;
    List<RoomUser> data = new LinkedList<>();
    ArrayList<Integer> selectedimagepaths;
    Drawable oldDrawable;
    int save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryInstaHashtagBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AdMob.loadAd2(this);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdView mAdView;
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest1 = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest1);

        Intent re = getIntent();
        save = re.getIntExtra("save", 0);

        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        List<RoomUser> Type = db.userDao().getAllUsers();

        for (int i = 0; i < Type.size(); i++) {
            if (Type.get(i).category.equals("InstaHashtag")){
                data.add(Type.get(i));
            }
        }

        if (data.size()>0){
            binding.data.setVisibility(View.GONE);
        }else {
            binding.data.setVisibility(View.VISIBLE);
            binding.delete.setVisibility(View.GONE);
        }

        Dialog coin = new Dialog(HistoryInstaHashtagActivity.this);
        coin.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(coin.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        coin.setContentView(R.layout.delet_dialog);
        coin.setCancelable(false);
        WindowManager.LayoutParams lpc = new WindowManager.LayoutParams();
        lpc.copyFrom(coin.getWindow().getAttributes());
        lpc.width = WindowManager.LayoutParams.MATCH_PARENT;
        lpc.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lpc.gravity = Gravity.CENTER;
        coin.getWindow().setAttributes(lpc);

        RelativeLayout delet = coin.findViewById(R.id.del);
        RelativeLayout cansal = coin.findViewById(R.id.can);

        cansal.setOnClickListener(view -> {
            coin.dismiss();
        });

        delet.setOnClickListener(view -> {
            for (int i = 0; i < selectedimagepaths.size(); i++) {
                db.userDao().deleteByUserId(selectedimagepaths.get(i));
                Toast.makeText(HistoryInstaHashtagActivity.this, "deleted", Toast.LENGTH_SHORT).show();
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });

        binding.selectall.setOnClickListener(v -> {
            if (selectedimagepaths.size() == data.size()) {
                selectedimagepaths = new ArrayList<>();
                binding.selectall.setImageResource(R.drawable.not_selact);
                binding.delete.setVisibility(View.GONE);
            } else {
                selectedimagepaths = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    selectedimagepaths.add(data.get(i).uid);
                }
                binding.delete.setVisibility(View.VISIBLE);
                binding.selectall.setImageResource(R.drawable.sellact_un);
            }
            binding.recy.getAdapter().notifyDataSetChanged();
        });

        binding.delete.setOnClickListener(view -> {
            coin.show();
        });

        selectedimagepaths = new ArrayList<>();

        binding.recy.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager layoutManager2 = new GridLayoutManager(this, 2);
        binding.recy.setLayoutManager(layoutManager2);
        adapter = new historyAdapter(this, data);
        binding.recy.setAdapter(adapter);

        binding.back.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    public class historyAdapter extends RecyclerView.Adapter<historyAdapter.MyViewHolder> {
        private final Context context;
        private List<RoomUser> userList;

        public historyAdapter(Context context, List<RoomUser> userList) {
            this.context = context;
            this.userList = userList;
        }

        @NonNull
        @Override
        public historyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.history_recycal, parent, false);
            return new historyAdapter.MyViewHolder(view);
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onBindViewHolder(@NonNull historyAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.Retitle.setText(userList.get(position).title);
            holder.Redata.setText(userList.get(position).allData);

            if (selectedimagepaths.size() > 0) {
                binding.delete.setVisibility(View.VISIBLE);
                if (selectedimagepaths.contains(userList.get(position).uid)) {
                    holder.chake.setImageResource(R.drawable.sellact_un);
                } else {
                    holder.chake.setImageResource(R.drawable.not_selact);
                }
            } else {
                binding.delete.setVisibility(View.GONE);
                holder.chake.setImageResource(R.drawable.not_selact);
            }
            if (selectedimagepaths.size() == userList.size()) {
                binding.selectall.setImageResource(R.drawable.sellact_un);
                oldDrawable = binding.selectall.getDrawable();
            } else {
                binding.selectall.setImageResource(R.drawable.not_selact);
            }

            holder.chake.setOnClickListener(v -> {
                if (selectedimagepaths.contains(userList.get(position).uid)) {
                    selectedimagepaths.remove((Integer) userList.get(position).uid);
                } else {
                    selectedimagepaths.add(userList.get(position).uid);
                }
                if (selectedimagepaths.size() > 0) {
                    binding.delete.setVisibility(View.VISIBLE);
                } else {
                    binding.delete.setVisibility(View.GONE);
                }
                notifyDataSetChanged();

            });

            holder.move.setOnClickListener(view -> {
                Intent i = new Intent(HistoryInstaHashtagActivity.this, ResultActivity.class);
                i.putExtra("result", userList.get(position).allData);
                i.putExtra("Query",userList.get(position).query);
                i.putExtra("category",userList.get(position).category);
                i.putExtra("title",userList.get(position).title);
                i.putExtra("save",0);
                startActivity(i);
                finish();
            });

        }

        @Override
        public int getItemCount() {
            return userList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView Retitle, Redata;
            ImageView chake;
            LinearLayout move;
            public MyViewHolder(View view) {
                super(view);
                chake = view.findViewById(R.id.chake);
                Retitle = view.findViewById(R.id.Retitle);
                Redata = view.findViewById(R.id.Redata);
                move = view.findViewById(R.id.move);
            }
        }
    }

    @Override
    public void onBackPressed() {
        new AdMob(new OnDismiss() {
            @Override
            public void onDismiss() {
                if (save == 1){
                    startActivity(new Intent(HistoryInstaHashtagActivity.this, StartMenuActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(HistoryInstaHashtagActivity.this, HistoryMenuActivity.class));
                    finish();
                }
            }
        }).showAds2(HistoryInstaHashtagActivity.this, true);

    }

}