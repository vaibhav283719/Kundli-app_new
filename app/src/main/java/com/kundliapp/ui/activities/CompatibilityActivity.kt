package com.kundliapp.ui.activities

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kundliapp.R
import com.kundliapp.databinding.ActivityCompatibilityBinding
import com.kundliapp.domain.models.CompatibilityLevel
import com.kundliapp.ui.viewmodels.CompatibilityViewModel
import com.kundliapp.utils.AdMobHelper
import com.kundliapp.utils.DateTimeUtils

class CompatibilityActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCompatibilityBinding
    private val viewModel: CompatibilityViewModel by viewModels()
    private lateinit var adMobHelper: AdMobHelper

    private var year1 = DateTimeUtils.getCurrentYear()
    private var month1 = DateTimeUtils.getCurrentMonth()
    private var day1 = DateTimeUtils.getCurrentDay()
    private var hour1 = 12; private var minute1 = 0

    private var year2 = DateTimeUtils.getCurrentYear()
    private var month2 = DateTimeUtils.getCurrentMonth()
    private var day2 = DateTimeUtils.getCurrentDay()
    private var hour2 = 12; private var minute2 = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompatibilityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.compatibility)

        adMobHelper = AdMobHelper(this)
        adMobHelper.loadBannerAd(binding.adContainer)

        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        updateDisplays()

        binding.btnDate1.setOnClickListener {
            DatePickerDialog(this, { _, y, m, d -> year1 = y; month1 = m + 1; day1 = d; updateDisplays() }, year1, month1 - 1, day1).show()
        }
        binding.btnTime1.setOnClickListener {
            TimePickerDialog(this, { _, h, m -> hour1 = h; minute1 = m; updateDisplays() }, hour1, minute1, true).show()
        }
        binding.btnDate2.setOnClickListener {
            DatePickerDialog(this, { _, y, m, d -> year2 = y; month2 = m + 1; day2 = d; updateDisplays() }, year2, month2 - 1, day2).show()
        }
        binding.btnTime2.setOnClickListener {
            TimePickerDialog(this, { _, h, m -> hour2 = h; minute2 = m; updateDisplays() }, hour2, minute2, true).show()
        }

        binding.btnCalculate.setOnClickListener {
            viewModel.calculateCompatibility(
                year1, month1, day1, hour1, minute1, 20.5937, 78.9629,
                year2, month2, day2, hour2, minute2, 20.5937, 78.9629
            )
        }
    }

    private fun updateDisplays() {
        binding.tvDate1.text = DateTimeUtils.formatDate(year1, month1, day1)
        binding.tvTime1.text = DateTimeUtils.formatTime(hour1, minute1)
        binding.tvDate2.text = DateTimeUtils.formatDate(year2, month2, day2)
        binding.tvTime2.text = DateTimeUtils.formatTime(hour2, minute2)
    }

    private fun observeViewModel() {
        viewModel.result.observe(this) { result ->
            if (result != null) {
                binding.groupResult.visibility = View.VISIBLE
                binding.tvScore.text = "${result.totalScore}/${result.maxScore}"
                binding.tvPercentage.text = "${result.percentage}%"
                binding.progressCompatibility.progress = result.percentage
                binding.tvLevel.text = when (result.level) {
                    CompatibilityLevel.EXCELLENT -> "⭐ Excellent Match!"
                    CompatibilityLevel.GOOD -> "✅ Good Match"
                    CompatibilityLevel.AVERAGE -> "🔄 Average Match"
                    CompatibilityLevel.BELOW_AVERAGE -> "⚠️ Below Average"
                    CompatibilityLevel.POOR -> "❌ Challenging Match"
                }
                binding.tvDescription.text = result.description
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
