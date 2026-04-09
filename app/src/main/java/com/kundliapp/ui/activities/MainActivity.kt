package com.kundliapp.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.kundliapp.R
import com.kundliapp.databinding.ActivityMainBinding
import com.kundliapp.utils.AdMobHelper

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adMobHelper: AdMobHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
        setupAds()
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)
    }

    private fun setupAds() {
        adMobHelper = AdMobHelper(this)
        adMobHelper.loadBannerAd(binding.adContainer)
        adMobHelper.loadInterstitialAd()
    }

    fun showInterstitialAd(onDismissed: (() -> Unit)? = null) {
        adMobHelper.showInterstitialAd(this, onDismissed)
    }
}
