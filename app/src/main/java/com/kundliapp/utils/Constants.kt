package com.kundliapp.utils

object Constants {

    // AdMob Test IDs - Replace with real IDs before publishing
    const val ADMOB_BANNER_ID = "ca-app-pub-3940256099942544/6300978111"
    const val ADMOB_INTERSTITIAL_ID = "ca-app-pub-3940256099942544/1033173712"
    const val ADMOB_REWARDED_ID = "ca-app-pub-3940256099942544/5224354917"

    // Intent Keys
    const val KEY_PROFILE_ID = "profile_id"
    const val KEY_ZODIAC_INDEX = "zodiac_index"
    const val KEY_PLANET_INDEX = "planet_index"
    const val KEY_HOUSE_INDEX = "house_index"
    const val KEY_HOROSCOPE_TYPE = "horoscope_type"
    const val KEY_BIRTH_CHART = "birth_chart"

    // Horoscope Types
    const val HOROSCOPE_DAILY = "daily"
    const val HOROSCOPE_WEEKLY = "weekly"
    const val HOROSCOPE_MONTHLY = "monthly"
    const val HOROSCOPE_YEARLY = "yearly"

    // Zodiac Signs
    val ZODIAC_SIGNS = listOf(
        "Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo",
        "Libra", "Scorpio", "Sagittarius", "Capricorn", "Aquarius", "Pisces"
    )

    val ZODIAC_SYMBOLS = listOf(
        "♈", "♉", "♊", "♋", "♌", "♍", "♎", "♏", "♐", "♑", "♒", "♓"
    )

    val ZODIAC_ELEMENTS = listOf(
        "Fire", "Earth", "Air", "Water", "Fire", "Earth",
        "Air", "Water", "Fire", "Earth", "Air", "Water"
    )

    val ZODIAC_RULING_PLANETS = listOf(
        "Mars", "Venus", "Mercury", "Moon", "Sun", "Mercury",
        "Venus", "Mars/Pluto", "Jupiter", "Saturn", "Saturn/Uranus", "Jupiter/Neptune"
    )

    // Planets
    val PLANETS = listOf("Sun", "Moon", "Mars", "Mercury", "Jupiter", "Venus", "Saturn", "Rahu", "Ketu")

    // Nakshatras
    val NAKSHATRAS = listOf(
        "Ashwini", "Bharani", "Krittika", "Rohini", "Mrigashira", "Ardra",
        "Punarvasu", "Pushya", "Ashlesha", "Magha", "Purva Phalguni", "Uttara Phalguni",
        "Hasta", "Chitra", "Swati", "Vishakha", "Anuradha", "Jyeshtha",
        "Mula", "Purva Ashadha", "Uttara Ashadha", "Shravana", "Dhanishtha",
        "Shatabhisha", "Purva Bhadrapada", "Uttara Bhadrapada", "Revati"
    )

    // House Meanings
    val HOUSE_MEANINGS = listOf(
        "Self, Appearance, Personality",
        "Wealth, Family, Speech",
        "Siblings, Courage, Communication",
        "Mother, Home, Happiness",
        "Intelligence, Children, Creativity",
        "Enemies, Health, Service",
        "Marriage, Partnership, Business",
        "Death, Transformation, Inheritance",
        "Religion, Higher Learning, Travel",
        "Career, Fame, Status",
        "Gains, Hopes, Social Network",
        "Loss, Expenses, Liberation"
    )
}
