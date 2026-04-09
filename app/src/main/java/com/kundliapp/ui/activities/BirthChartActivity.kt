package com.kundliapp.ui.activities

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kundliapp.R
import com.kundliapp.databinding.ActivityBirthChartBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.kundliapp.ui.adapters.PlanetPositionAdapter
import com.kundliapp.ui.viewmodels.BirthChartViewModel
import com.kundliapp.utils.AdMobHelper
import com.kundliapp.utils.DateTimeUtils
import java.util.Calendar

class BirthChartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBirthChartBinding
    private val viewModel: BirthChartViewModel by viewModels()
    private lateinit var adMobHelper: AdMobHelper

    private var selectedYear = DateTimeUtils.getCurrentYear()
    private var selectedMonth = DateTimeUtils.getCurrentMonth()
    private var selectedDay = DateTimeUtils.getCurrentDay()
    private var selectedHour = 12
    private var selectedMinute = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBirthChartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.birth_chart)

        adMobHelper = AdMobHelper(this)
        adMobHelper.loadBannerAd(binding.adContainer)

        setupUI()
        observeViewModel()
        updateDateDisplay()
        updateTimeDisplay()

        binding.rvPlanetPositions.layoutManager = LinearLayoutManager(this)
    }

    private fun setupUI() {
        binding.btnSelectDate.setOnClickListener { showDatePicker() }
        binding.btnSelectTime.setOnClickListener { showTimePicker() }
        binding.btnCalculate.setOnClickListener { calculateChart() }
        binding.btnSaveProfile.setOnClickListener { saveProfile() }
    }

    private fun observeViewModel() {
        viewModel.birthChart.observe(this) { chart ->
            if (chart != null) {
                binding.groupResults.visibility = View.VISIBLE
                binding.tvSunSign.text = "☀ ${chart.sunSign}"
                binding.tvMoonSign.text = "☽ ${chart.moonSign}"
                binding.tvAscendant.text = "↑ ${chart.ascendant}"
                binding.tvNakshatra.text = "★ ${chart.nakshatra} (Pada ${chart.nakshatraPada})"

                val positions = chart.planetPositions.values.toList()
                binding.rvPlanetPositions.adapter = PlanetPositionAdapter(positions)
            }
        }

        viewModel.isLoading.observe(this) { loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
            binding.btnCalculate.isEnabled = !loading
        }

        viewModel.saveSuccess.observe(this) { success ->
            if (success) {
                Toast.makeText(this, getString(R.string.profile_saved), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showDatePicker() {
        DatePickerDialog(this, { _, year, month, day ->
            selectedYear = year
            selectedMonth = month + 1
            selectedDay = day
            updateDateDisplay()
        }, selectedYear, selectedMonth - 1, selectedDay).show()
    }

    private fun showTimePicker() {
        TimePickerDialog(this, { _, hour, minute ->
            selectedHour = hour
            selectedMinute = minute
            updateTimeDisplay()
        }, selectedHour, selectedMinute, true).show()
    }

    private fun updateDateDisplay() {
        binding.tvSelectedDate.text = DateTimeUtils.formatDate(selectedYear, selectedMonth, selectedDay)
    }

    private fun updateTimeDisplay() {
        binding.tvSelectedTime.text = DateTimeUtils.formatTime(selectedHour, selectedMinute)
    }

    private fun calculateChart() {
        val name = binding.etName.text.toString().trim()
        val birthPlace = binding.etBirthPlace.text.toString().trim()

        if (name.isEmpty()) {
            binding.etName.error = getString(R.string.enter_name)
            return
        }
        if (birthPlace.isEmpty()) {
            binding.etBirthPlace.error = getString(R.string.enter_birth_place)
            return
        }

        // Default to India coordinates if not geocoded
        val latitude = 20.5937
        val longitude = 78.9629

        viewModel.calculateChart(name, selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute, latitude, longitude)
    }

    private fun saveProfile() {
        val chart = viewModel.birthChart.value ?: return
        val name = binding.etName.text.toString().trim()
        val gender = if (binding.rbMale.isChecked) "Male" else "Female"
        val birthPlace = binding.etBirthPlace.text.toString().trim()

        viewModel.saveProfile(
            name, gender, selectedYear, selectedMonth, selectedDay,
            selectedHour, selectedMinute, birthPlace, 20.5937, 78.9629, chart
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
