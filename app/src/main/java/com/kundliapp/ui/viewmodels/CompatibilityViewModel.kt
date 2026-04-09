package com.kundliapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kundliapp.domain.models.CompatibilityLevel
import com.kundliapp.domain.models.CompatibilityResult
import com.kundliapp.utils.AstrologicalCalculations
import com.kundliapp.utils.Constants
import com.kundliapp.utils.DateTimeUtils

class CompatibilityViewModel : ViewModel() {

    private val _result = MutableLiveData<CompatibilityResult?>()
    val result: LiveData<CompatibilityResult?> = _result

    fun calculateCompatibility(
        year1: Int, month1: Int, day1: Int, hour1: Int, minute1: Int, lat1: Double, lon1: Double,
        year2: Int, month2: Int, day2: Int, hour2: Int, minute2: Int, lat2: Double, lon2: Double
    ) {
        val h1 = DateTimeUtils.timeToDecimalHour(hour1, minute1)
        val h2 = DateTimeUtils.timeToDecimalHour(hour2, minute2)

        val moon1 = AstrologicalCalculations.getMoonLongitude(year1, month1, day1, h1)
        val moon2 = AstrologicalCalculations.getMoonLongitude(year2, month2, day2, h2)

        val moonSign1 = AstrologicalCalculations.getZodiacSign(moon1)
        val moonSign2 = AstrologicalCalculations.getZodiacSign(moon2)
        val nakshatra1 = AstrologicalCalculations.getNakshatra(moon1)
        val nakshatra2 = AstrologicalCalculations.getNakshatra(moon2)

        val score = AstrologicalCalculations.calculateCompatibility(moonSign1, moonSign2, nakshatra1, nakshatra2)
        val maxScore = 36
        val percentage = (score * 100) / maxScore

        val level = when {
            percentage >= 80 -> CompatibilityLevel.EXCELLENT
            percentage >= 65 -> CompatibilityLevel.GOOD
            percentage >= 50 -> CompatibilityLevel.AVERAGE
            percentage >= 35 -> CompatibilityLevel.BELOW_AVERAGE
            else -> CompatibilityLevel.POOR
        }

        val description = when (level) {
            CompatibilityLevel.EXCELLENT -> "Exceptional compatibility! This is a blessed union with strong cosmic support. Your energies complement each other beautifully, bringing harmony, love, and mutual growth."
            CompatibilityLevel.GOOD -> "Good compatibility! You share a strong connection with positive energy. With understanding and care, this relationship will flourish and bring joy to both."
            CompatibilityLevel.AVERAGE -> "Average compatibility. Every relationship has its unique challenges and joys. With effort, communication, and understanding, you can build a meaningful relationship."
            CompatibilityLevel.BELOW_AVERAGE -> "Below average compatibility. There may be some challenges in this relationship. Patience, understanding and compromise are key to making this work."
            CompatibilityLevel.POOR -> "Low compatibility score. There may be significant differences in nature. Spiritual practices and understanding can help bridge the gap."
        }

        _result.value = CompatibilityResult(score, maxScore, percentage, level, description)
    }

    fun clearResult() {
        _result.value = null
    }
}
