package com.muiezarif.livewallpaper

import androidx.multidex.MultiDexApplication
import com.google.android.gms.ads.MobileAds

class MyApplication:MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this,getString(R.string.ad_app_id))
    }
}