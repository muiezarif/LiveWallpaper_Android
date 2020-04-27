package com.muiezarif.livewallpaper.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import com.muiezarif.livewallpaper.R
import com.muiezarif.livewallpaper.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    val context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        Handler().postDelayed(
            { intentCallForward(context, MainActivity::class.java) },
            SPLASH_DISPLAY_LENGTH.toLong()
        )
    }

    private fun intentCallForward(c: Context, cls: Class<*>) {
        val intentInfo = Intent(c, cls)
        startActivity(intentInfo)
        finish()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
    }

    companion object {
        const val SPLASH_DISPLAY_LENGTH = 3000
    }
}
