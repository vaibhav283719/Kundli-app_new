package com.kundliapp.domain.usecases

import com.kundliapp.domain.models.*
import com.kundliapp.utils.AstrologicalCalculations
import com.kundliapp.utils.Constants
import com.kundliapp.utils.DateTimeUtils

class CalculateBirthChartUseCase {

    operator fun invoke(
        name: String,
        year: Int,
        month: Int,
        day: Int,
        hour: Int,
        minute: Int,
        latitude: Double,
        longitude: Double
    ): BirthChart {
        val decimalHour = DateTimeUtils.timeToDecimalHour(hour, minute)

        // Calculate planetary positions
        val planetPositions = AstrologicalCalculations.getAllPlanetPositions(year, month, day, decimalHour)

        // Ascendant
        val ascDeg = AstrologicalCalculations.getAscendant(year, month, day, decimalHour, latitude, longitude)
        val ascSignIdx = AstrologicalCalculations.getZodiacSign(ascDeg)

        // Sun
        val sunDeg = planetPositions["Sun"]!!
        val sunSignIdx = AstrologicalCalculations.getZodiacSign(sunDeg)

        // Moon
        val moonDeg = planetPositions["Moon"]!!
        val moonSignIdx = AstrologicalCalculations.getZodiacSign(moonDeg)
        val nakshatraIdx = AstrologicalCalculations.getNakshatra(moonDeg)
        val nakshatraPada = AstrologicalCalculations.getNakshatraPada(moonDeg)

        // Houses
        val houseLongitudes = AstrologicalCalculations.calculateHouses(ascDeg)

        // Determine which house each planet is in
        fun getHouseForPlanet(planetLon: Double): Int {
            for (i in 11 downTo 0) {
                val houseLon = houseLongitudes[i]
                val adjustedPlanet = if (planetLon >= houseLon) planetLon - houseLon else planetLon - houseLon + 360
                val adjustedNext = if (i < 11) {
                    val nextLon = houseLongitudes[i + 1]
                    if (nextLon >= houseLon) nextLon - houseLon else nextLon - houseLon + 360
                } else {
                    360.0
                }
                if (adjustedPlanet < adjustedNext) return i + 1
            }
            return 1
        }

        // Build planet position objects
        val planetPositionMap = Constants.PLANETS.associateWith { planet ->
            val deg = planetPositions[planet] ?: 0.0
            val signIdx = AstrologicalCalculations.getZodiacSign(deg)
            val nakIdx = AstrologicalCalculations.getNakshatra(deg)
            PlanetPosition(
                planet = planet,
                longitude = deg,
                sign = Constants.ZODIAC_SIGNS[signIdx],
                signIndex = signIdx,
                degree = AstrologicalCalculations.getDegreeInSign(deg),
                nakshatra = Constants.NAKSHATRAS[nakIdx],
                house = getHouseForPlanet(deg)
            )
        }

        // Build house info
        val houseInfoList = houseLongitudes.mapIndexed { index, lon ->
            val signIdx = AstrologicalCalculations.getZodiacSign(lon)
            val planetsInThisHouse = Constants.PLANETS.filter { planet ->
                val pd = planetPositions[planet] ?: 0.0
                val nextLon = if (index < 11) houseLongitudes[index + 1] else houseLongitudes[0] + 360
                val adjustedPd = if (pd >= lon) pd - lon else pd - lon + 360
                val adjustedNext = if (nextLon >= lon) nextLon - lon else nextLon - lon + 360
                adjustedPd < adjustedNext
            }
            HouseInfo(
                houseNumber = index + 1,
                longitude = lon,
                sign = Constants.ZODIAC_SIGNS[signIdx],
                signIndex = signIdx,
                meaning = Constants.HOUSE_MEANINGS.getOrElse(index) { "" },
                planetsInHouse = planetsInThisHouse
            )
        }

        return BirthChart(
            name = name,
            sunSign = Constants.ZODIAC_SIGNS[sunSignIdx],
            sunSignIndex = sunSignIdx,
            moonSign = Constants.ZODIAC_SIGNS[moonSignIdx],
            moonSignIndex = moonSignIdx,
            ascendant = Constants.ZODIAC_SIGNS[ascSignIdx],
            ascendantIndex = ascSignIdx,
            ascendantDegree = ascDeg,
            nakshatra = Constants.NAKSHATRAS[nakshatraIdx],
            nakshatraIndex = nakshatraIdx,
            nakshatraPada = nakshatraPada,
            planetPositions = planetPositionMap,
            houses = houseInfoList
        )
    }
}
