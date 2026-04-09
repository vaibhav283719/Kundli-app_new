package com.kundliapp.ui.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.kundliapp.data.database.AppDatabase
import com.kundliapp.data.database.entities.BirthProfile
import com.kundliapp.data.repository.BirthProfileRepository
import com.kundliapp.domain.models.BirthChart
import com.kundliapp.domain.usecases.CalculateBirthChartUseCase
import com.kundliapp.utils.Constants
import kotlinx.coroutines.launch

class BirthChartViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: BirthProfileRepository
    private val calculateBirthChart = CalculateBirthChartUseCase()

    val allProfiles: LiveData<List<BirthProfile>>

    private val _birthChart = MutableLiveData<BirthChart?>()
    val birthChart: LiveData<BirthChart?> = _birthChart

    private val _saveSuccess = MutableLiveData<Boolean>()
    val saveSuccess: LiveData<Boolean> = _saveSuccess

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        val dao = AppDatabase.getDatabase(application).birthProfileDao()
        repository = BirthProfileRepository(dao)
        allProfiles = repository.allProfiles
    }

    fun calculateChart(
        name: String,
        year: Int,
        month: Int,
        day: Int,
        hour: Int,
        minute: Int,
        latitude: Double,
        longitude: Double
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            val chart = calculateBirthChart(name, year, month, day, hour, minute, latitude, longitude)
            _birthChart.value = chart
            _isLoading.value = false
        }
    }

    fun saveProfile(
        name: String,
        gender: String,
        year: Int,
        month: Int,
        day: Int,
        hour: Int,
        minute: Int,
        birthPlace: String,
        latitude: Double,
        longitude: Double,
        chart: BirthChart
    ) {
        viewModelScope.launch {
            val profile = BirthProfile(
                name = name,
                gender = gender,
                birthDay = day,
                birthMonth = month,
                birthYear = year,
                birthHour = hour,
                birthMinute = minute,
                birthPlace = birthPlace,
                latitude = latitude,
                longitude = longitude,
                sunSign = chart.sunSign,
                moonSign = chart.moonSign,
                ascendant = chart.ascendant,
                nakshatra = chart.nakshatra
            )
            repository.insertProfile(profile)
            _saveSuccess.value = true
        }
    }

    fun deleteProfile(profile: BirthProfile) {
        viewModelScope.launch {
            repository.deleteProfile(profile)
        }
    }

    fun clearChart() {
        _birthChart.value = null
    }
}
