package com.muiezarif.livewallpaper.helpers

import android.content.Context
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd


object AdHelper {
    lateinit var adRequestBanner:AdRequest
    lateinit var adRequestInterstitial:AdRequest
    lateinit var mInterstitialAd: InterstitialAd

    fun adRequestBanner(){
        adRequestBanner=AdRequest.Builder().build()
    }
    fun adRequestInterstitial(){
        adRequestInterstitial=AdRequest.Builder().build()
    }
    fun loadBannerAd(adView: AdView){
        adRequestBanner()
        adView.loadAd(adRequestBanner)
    }
    fun loadInterstitialAd(context:Context,adUnitId:String){
        adRequestInterstitial()
        mInterstitialAd = InterstitialAd(context)
        mInterstitialAd.adUnitId = adUnitId
        mInterstitialAd.loadAd(adRequestInterstitial)
        mInterstitialAd.adListener= object : AdListener() {
            override fun onAdImpression() {
                super.onAdImpression()
            }

            override fun onAdLeftApplication() {
                super.onAdLeftApplication()
            }

            override fun onAdClicked() {
                super.onAdClicked()
            }

            override fun onAdFailedToLoad(p0: Int) {
                super.onAdFailedToLoad(p0)
                adRequestInterstitial()
                mInterstitialAd.loadAd(adRequestInterstitial)
            }

            override fun onAdClosed() {
                super.onAdClosed()
                adRequestInterstitial()
                mInterstitialAd.loadAd(adRequestInterstitial)
            }

            override fun onAdOpened() {
                super.onAdOpened()
            }

            override fun onAdLoaded() {
                super.onAdLoaded()
            }
        }
    }
    fun reloadInterstitialAd(){
        adRequestInterstitial()
        mInterstitialAd.loadAd(adRequestInterstitial)
    }

    fun showInterstitialAd(){
        if (mInterstitialAd != null && mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
        }
    }
}