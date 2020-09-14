package com.guardianapps.admobintegerationdemo;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class AdsHandling {

    /**
     * Created by Faraz Hussain on 1/12/2018.
     */

    private static AdsHandling instance;
    private InterstitialAd mInterstitialAd;
    private AdRequest adRequest;
    private AdListener adListener = new AdListener() {

        @Override
        public void onAdClosed() {
            mInterstitialAd.loadAd(getAdRequest());
        }
    };

    public static AdsHandling getInstance() {
        if (instance == null) {
            instance = new AdsHandling();
            return instance;
        } else {
            return instance;
        }
    }

    public void initAds(final Context context) {
        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Toast.makeText(context, "Initialized Sdk", Toast.LENGTH_SHORT).show();
            }
        });
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(context.getString(R.string.interstitial_id));
        mInterstitialAd.loadAd(getAdRequest());
        mInterstitialAd.setAdListener(getAdListener());

    }
    public boolean showSplashAds(Context context) {
        if (mInterstitialAd != null) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
                return true;
            }
        } else {
            initAds(context);
        }
        return false;
    }


    public AdRequest getAdRequest() {
        try {
            if (adRequest == null) {
                adRequest = new AdRequest.Builder().build();
            }
        } catch (Exception ignored) {
        }

        return adRequest;

    }

    public AdListener getAdListener() {
        if (adListener == null) {
            adListener = new AdListener() {

                @Override
                public void onAdClosed() {
                    if (mInterstitialAd != null)
                        mInterstitialAd.loadAd(getAdRequest());

                }
            };
            return adListener;
        } else
            return adListener;
    }

    public void setDefaultAdListener() {
        if (mInterstitialAd != null) {
            mInterstitialAd.setAdListener(null);
            mInterstitialAd.setAdListener(getAdListener());
        }

    }

    public void loadInterstitial() {
        if (mInterstitialAd != null) {
            mInterstitialAd.loadAd(getAdRequest());
        }
    }

    public void setAdListener(AdListener adListener) {
        if (mInterstitialAd != null) {
            mInterstitialAd.setAdListener(null);
            mInterstitialAd.setAdListener(adListener);
        }
    }

    public void setAdRequest() {
        if (mInterstitialAd != null) {
            mInterstitialAd.loadAd(getAdRequest());
        }
    }

    public void loadAdaptiveBanner(Context context, FrameLayout frameLayout) {
        AdView adView = new AdView(context);
        String bannerAdaptiveId = context.getString(R.string.banner_id);
        adView.setAdUnitId(bannerAdaptiveId);
        frameLayout.addView(adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        AdSize adSize = getAdSize(context);
        adView.setAdSize(adSize);
        adView.loadAd(adRequest);
    }

    private AdSize getAdSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;
        int adWidth = (int) (widthPixels / density);
        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth);
    }

}

