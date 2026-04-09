package com.kundliapp.ui.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kundliapp.R
import com.kundliapp.databinding.ActivityHoroscopeBinding
import com.kundliapp.ui.viewmodels.HoroscopeViewModel
import com.kundliapp.utils.AdMobHelper
import com.kundliapp.utils.Constants

class HoroscopeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHoroscopeBinding
    private val viewModel: HoroscopeViewModel by viewModels()
    private lateinit var adMobHelper: AdMobHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHoroscopeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.horoscope)

        adMobHelper = AdMobHelper(this)
        adMobHelper.loadBannerAd(binding.adContainer)
        adMobHelper.loadRewardedAd()

        val zodiacIndex = intent.getIntExtra(Constants.KEY_ZODIAC_INDEX, 0)
        viewModel.selectZodiac(zodiacIndex)

        setupTabLayout()
        observeViewModel()
    }

    private fun setupTabLayout() {
        val tabs = listOf(
            getString(R.string.daily),
            getString(R.string.weekly),
            getString(R.string.monthly),
            getString(R.string.yearly)
        )
        tabs.forEach { binding.tabLayout.addTab(binding.tabLayout.newTab().setText(it)) }

        binding.tabLayout.addOnTabSelectedListener(object : com.google.android.material.tabs.TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: com.google.android.material.tabs.TabLayout.Tab) {
                val type = when (tab.position) {
                    0 -> Constants.HOROSCOPE_DAILY
                    1 -> Constants.HOROSCOPE_WEEKLY
                    2 -> Constants.HOROSCOPE_MONTHLY
                    3 -> Constants.HOROSCOPE_YEARLY
                    else -> Constants.HOROSCOPE_DAILY
                }
                viewModel.setHoroscopeType(type)
            }
            override fun onTabUnselected(tab: com.google.android.material.tabs.TabLayout.Tab) {}
            override fun onTabReselected(tab: com.google.android.material.tabs.TabLayout.Tab) {}
        })
    }

    private fun observeViewModel() {
        viewModel.selectedZodiacIndex.observe(this) { index ->
            val sign = Constants.ZODIAC_SIGNS[index]
            val symbol = Constants.ZODIAC_SYMBOLS[index]
            binding.tvZodiacName.text = "$symbol $sign"
            binding.tvElement.text = "${Constants.ZODIAC_ELEMENTS[index]} Sign"
            binding.tvRulingPlanet.text = "Ruled by ${Constants.ZODIAC_RULING_PLANETS[index]}"
        }

        viewModel.horoscopeText.observe(this) { text ->
            binding.tvHoroscopeText.text = text
        }

        viewModel.luckyNumbers.observe(this) { numbers ->
            binding.tvLuckyNumbers.text = getString(R.string.lucky_numbers, numbers.joinToString(", "))
        }

        viewModel.luckyColors.observe(this) { colors ->
            binding.tvLuckyColors.text = getString(R.string.lucky_colors, colors.joinToString(", "))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
