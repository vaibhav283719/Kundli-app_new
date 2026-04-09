package com.kundliapp.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.FrameLayout
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

/**
 * Centralized AdMob management helper.
 * Handles banner, interstitial, and rewarded ad loading and display.
 */
class AdMobHelper(private val context: Context) {

    private var interstitialAd: InterstitialAd? = null
    private var rewardedAd: RewardedAd? = null

    companion object {
        private const val TAG = "AdMobHelper"

        fun initialize(context: Context) {
            MobileAds.initialize(context) { initStatus ->
                Log.d(TAG, "AdMob initialized: ${initStatus.adapterStatusMap}")
            }
        }
    }

    /**
     * Load and display a banner ad in the given container
     */
    fun loadBannerAd(container: FrameLayout) {
        val adView = AdView(context)
        adView.setAdSize(AdSize.BANNER)
        adView.adUnitId = Constants.ADMOB_BANNER_ID
        container.addView(adView)

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
        adView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                Log.d(TAG, "Banner ad loaded")
            }
            override fun onAdFailedToLoad(error: LoadAdError) {
                Log.e(TAG, "Banner ad failed: ${error.message}")
            }
        }
    }

    /**
     * Preload interstitial ad
     */
    fun loadInterstitialAd(onLoaded: (() -> Unit)? = null) {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            context,
            Constants.ADMOB_INTERSTITIAL_ID,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                    Log.d(TAG, "Interstitial ad loaded")
                    onLoaded?.invoke()
                }
                override fun onAdFailedToLoad(error: LoadAdError) {
                    interstitialAd = null
                    Log.e(TAG, "Interstitial ad failed: ${error.message}")
                }
            }
        )
    }

    /**
     * Show interstitial ad if loaded
     */
    fun showInterstitialAd(activity: Activity, onDismissed: (() -> Unit)? = null) {
        if (interstitialAd != null) {
            interstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    interstitialAd = null
                    loadInterstitialAd()
                    onDismissed?.invoke()
                }
                override fun onAdFailedToShowFullScreenContent(error: AdError) {
                    interstitialAd = null
                    onDismissed?.invoke()
                }
            }
            interstitialAd?.show(activity)
        } else {
            onDismissed?.invoke()
            loadInterstitialAd()
        }
    }

    /**
     * Preload rewarded ad
     */
    fun loadRewardedAd(onLoaded: (() -> Unit)? = null) {
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(
            context,
            Constants.ADMOB_REWARDED_ID,
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedAd) {
                    rewardedAd = ad
                    Log.d(TAG, "Rewarded ad loaded")
                    onLoaded?.invoke()
                }
                override fun onAdFailedToLoad(error: LoadAdError) {
                    rewardedAd = null
                    Log.e(TAG, "Rewarded ad failed: ${error.message}")
                }
            }
        )
    }

    /**
     * Show rewarded ad
     */
    fun showRewardedAd(activity: Activity, onReward: (() -> Unit)? = null, onDismissed: (() -> Unit)? = null) {
        if (rewardedAd != null) {
            rewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    rewardedAd = null
                    loadRewardedAd()
                    onDismissed?.invoke()
                }
                override fun onAdFailedToShowFullScreenContent(error: AdError) {
                    rewardedAd = null
                    onDismissed?.invoke()
                }
            }
            rewardedAd?.show(activity) { rewardItem ->
                Log.d(TAG, "User earned reward: ${rewardItem.amount} ${rewardItem.type}")
                onReward?.invoke()
            }
        } else {
            onDismissed?.invoke()
            loadRewardedAd()
        }
    }

    fun isInterstitialReady() = interstitialAd != null
    fun isRewardedReady() = rewardedAd != null
}
