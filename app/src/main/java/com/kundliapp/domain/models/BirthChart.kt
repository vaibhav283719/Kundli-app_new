package com.kundliapp.domain.models

data class BirthChart(
    val name: String,
    val sunSign: String,
    val sunSignIndex: Int,
    val moonSign: String,
    val moonSignIndex: Int,
    val ascendant: String,
    val ascendantIndex: Int,
    val ascendantDegree: Double,
    val nakshatra: String,
    val nakshatraIndex: Int,
    val nakshatraPada: Int,
    val planetPositions: Map<String, PlanetPosition>,
    val houses: List<HouseInfo>
)

data class PlanetPosition(
    val planet: String,
    val longitude: Double,
    val sign: String,
    val signIndex: Int,
    val degree: Double,
    val nakshatra: String,
    val house: Int
)

data class HouseInfo(
    val houseNumber: Int,
    val longitude: Double,
    val sign: String,
    val signIndex: Int,
    val meaning: String,
    val planetsInHouse: List<String> = emptyList()
)

data class CompatibilityResult(
    val totalScore: Int,
    val maxScore: Int,
    val percentage: Int,
    val level: CompatibilityLevel,
    val description: String
)

enum class CompatibilityLevel {
    EXCELLENT, GOOD, AVERAGE, BELOW_AVERAGE, POOR
}
