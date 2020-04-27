package com.muiezarif.livewallpaper.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.google.firebase.analytics.FirebaseAnalytics
import com.muiezarif.livewallpaper.R
import com.muiezarif.livewallpaper.adapters.MainTabLayoutAdapter
import com.muiezarif.livewallpaper.databinding.ActivityMainBinding
import com.muiezarif.livewallpaper.helpers.AdHelper

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var myAdapter: MainTabLayoutAdapter
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w: Window = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        AdHelper.loadInterstitialAd(this,getString(R.string.full_ad))

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        myAdapter= MainTabLayoutAdapter(supportFragmentManager)
        binding.vpClockSettings.adapter=myAdapter
        binding.tlClockSettings.setupWithViewPager(binding.vpClockSettings)

    }



    override fun onResume() {
        super.onResume()
        AdHelper.showInterstitialAd()
    }


    override fun onStop() {
        super.onStop()
        AdHelper.reloadInterstitialAd()
    }
}
