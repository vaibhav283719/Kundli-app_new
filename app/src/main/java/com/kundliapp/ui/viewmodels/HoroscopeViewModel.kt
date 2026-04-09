package com.kundliapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kundliapp.utils.AstrologicalCalculations
import com.kundliapp.utils.Constants

class HoroscopeViewModel : ViewModel() {

    private val _selectedZodiacIndex = MutableLiveData(0)
    val selectedZodiacIndex: LiveData<Int> = _selectedZodiacIndex

    private val _horoscopeType = MutableLiveData(Constants.HOROSCOPE_DAILY)
    val horoscopeType: LiveData<String> = _horoscopeType

    private val _horoscopeText = MutableLiveData<String>()
    val horoscopeText: LiveData<String> = _horoscopeText

    private val _luckyNumbers = MutableLiveData<List<Int>>()
    val luckyNumbers: LiveData<List<Int>> = _luckyNumbers

    private val _luckyColors = MutableLiveData<List<String>>()
    val luckyColors: LiveData<List<String>> = _luckyColors

    fun selectZodiac(index: Int) {
        _selectedZodiacIndex.value = index
        generateHoroscope()
    }

    fun setHoroscopeType(type: String) {
        _horoscopeType.value = type
        generateHoroscope()
    }

    fun generateHoroscope() {
        val zodiacIdx = _selectedZodiacIndex.value ?: 0
        val type = _horoscopeType.value ?: Constants.HOROSCOPE_DAILY
        _horoscopeText.value = AstrologicalCalculations.generateHoroscope(zodiacIdx, type)
        _luckyNumbers.value = AstrologicalCalculations.getLuckyNumbers(zodiacIdx)
        _luckyColors.value = AstrologicalCalculations.getLuckyColors(zodiacIdx)
    }

    init {
        generateHoroscope()
    }
}
