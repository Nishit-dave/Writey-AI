package com.app.writy.ads;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class AdMob {

    static OnDismiss onDismiss;
    public AdMob(OnDismiss onDismiss){
        this.onDismiss = onDismiss;
    }
    public AdMob(){
    }

    public static void loadAd(Context context){

        if (AdsUnit.isAds) {
            MobileAds.initialize(context, initializationStatus -> {

            });
            AdRequest adRequest = new AdRequest.Builder().build();
            RewardedAd.load(context, AdsUnit.Reword,
                    adRequest, new RewardedAdLoadCallback() {
                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            Log.d(TAG, loadAdError.toString());
                            AdsUnit.rewardedAd = null;
                        }

                        @Override
                        public void onAdLoaded(@NonNull RewardedAd ad) {
                            AdsUnit.rewardedAd = ad;
                        }
                    });
        }
    }

    public void showAds(Activity activity, boolean isLode){

        if (AdsUnit.rewardedAd != null){
            AdsUnit.rewardedAd.show(activity, rewardItem -> {

            });

            AdsUnit.rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    if (isLode){
                        AdsUnit.rewardedAd = null;
                        AdMob.loadAd(activity);
                    }
                    onDismiss.onDismiss();
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    super.onAdFailedToShowFullScreenContent(adError);
                }
            });
        }else {
            Toast.makeText(activity, "Please Press again..", Toast.LENGTH_LONG).show();
        }

    }

    public static void loadAd2(Context context){

        if (AdsUnit.isAds) {
            MobileAds.initialize(context, initializationStatus -> {

            });
            AdRequest adRequest = new AdRequest.Builder().build();
            RewardedAd.load(context, AdsUnit.Reword2,
                    adRequest, new RewardedAdLoadCallback() {
                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            Log.d(TAG, loadAdError.toString());
                            AdsUnit.rewardedAd = null;
                        }

                        @Override
                        public void onAdLoaded(@NonNull RewardedAd ad) {
                            AdsUnit.rewardedAd = ad;
                        }
                    });
        }
    }

    public void showAds2(Activity activity, boolean isLode){

        if (AdsUnit.rewardedAd != null){
            AdsUnit.rewardedAd.show(activity, rewardItem -> {

            });

            AdsUnit.rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    if (isLode){
                        AdsUnit.rewardedAd = null;
                        AdMob.loadAd2(activity);
                    }
                    onDismiss.onDismiss();
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    super.onAdFailedToShowFullScreenContent(adError);
                    onDismiss.onDismiss();
                }
            });
        }else {
            onDismiss.onDismiss();
        }

    }

}
