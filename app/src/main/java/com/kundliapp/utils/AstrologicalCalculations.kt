package com.kundliapp.utils

import java.util.Calendar
import kotlin.math.*

/**
 * Core astrological calculations engine.
 * Performs birth chart generation, planetary position calculations,
 * house calculations, and horoscope generation.
 */
object AstrologicalCalculations {

    // Julian Day calculation
    private fun julianDay(year: Int, month: Int, day: Int, hour: Double): Double {
        val y = if (month <= 2) year - 1 else year
        val m = if (month <= 2) month + 12 else month
        val a = (y / 100).toInt()
        val b = 2 - a + (a / 4)
        return floor(365.25 * (y + 4716)) + floor(30.6001 * (m + 1)) + day + hour / 24.0 + b - 1524.5
    }

    // Convert degrees to radians
    private fun toRad(deg: Double) = deg * PI / 180.0

    // Convert radians to degrees
    private fun toDeg(rad: Double) = rad * 180.0 / PI

    // Normalize angle to 0-360
    private fun normalize(deg: Double): Double {
        var d = deg % 360.0
        if (d < 0) d += 360.0
        return d
    }

    /**
     * Get zodiac sign index (0-11) for a given degree
     */
    fun getZodiacSign(degree: Double): Int {
        return (normalize(degree) / 30.0).toInt()
    }

    /**
     * Get nakshatra index (0-26) for a given degree
     */
    fun getNakshatra(degree: Double): Int {
        return (normalize(degree) / (360.0 / 27.0)).toInt()
    }

    /**
     * Get nakshatra pada (1-4) for a given degree
     */
    fun getNakshatraPada(degree: Double): Int {
        val nakshatraDeg = normalize(degree) % (360.0 / 27.0)
        return (nakshatraDeg / (360.0 / 108.0)).toInt() + 1
    }

    /**
     * Calculate Sun longitude (simplified VSOP87)
     */
    fun getSunLongitude(year: Int, month: Int, day: Int, hour: Double): Double {
        val jd = julianDay(year, month, day, hour)
        val t = (jd - 2451545.0) / 36525.0
        val l0 = 280.46646 + 36000.76983 * t
        val m = toRad(357.52911 + 35999.05029 * t)
        val c = (1.914602 - 0.004817 * t) * sin(m) + 0.019993 * sin(2 * m) + 0.000290 * sin(3 * m)
        val sunLon = normalize(l0 + c)
        // Apply ayanamsa (Lahiri) for Vedic
        val ayanamsa = 23.85 + (jd - 2415020.0) / 365.25 * 0.0136806
        return normalize(sunLon - ayanamsa)
    }

    /**
     * Calculate Moon longitude (simplified)
     */
    fun getMoonLongitude(year: Int, month: Int, day: Int, hour: Double): Double {
        val jd = julianDay(year, month, day, hour)
        val t = (jd - 2451545.0) / 36525.0
        val l = 218.3164477 + 481267.88123421 * t
        val m = toRad(357.5291092 + 35999.0502909 * t)
        val mMoon = toRad(134.9633964 + 477198.8675055 * t)
        val d = toRad(297.8501921 + 445267.1114034 * t)
        val f = toRad(93.2720950 + 483202.0175233 * t)
        val moonLon = l + 6.289 * sin(mMoon) - 1.274 * sin(mMoon - 2 * d) + 0.658 * sin(2 * d) -
                0.214 * sin(2 * mMoon) - 0.186 * sin(m) - 0.114 * sin(2 * f)
        val ayanamsa = 23.85 + (jd - 2415020.0) / 365.25 * 0.0136806
        return normalize(moonLon - ayanamsa)
    }

    /**
     * Calculate Mars longitude (simplified)
     */
    fun getMarsLongitude(year: Int, month: Int, day: Int, hour: Double): Double {
        val jd = julianDay(year, month, day, hour)
        val t = (jd - 2451545.0) / 36525.0
        val l = normalize(355.433 + 19140.2993 * t)
        val m = toRad(19.373 + 19140.2993 * t)
        val marsLon = l + 10.691 * sin(m) + 0.623 * sin(2 * m) + 0.050 * sin(3 * m)
        val ayanamsa = 23.85 + (jd - 2415020.0) / 365.25 * 0.0136806
        return normalize(marsLon - ayanamsa)
    }

    /**
     * Calculate Mercury longitude (simplified)
     */
    fun getMercuryLongitude(year: Int, month: Int, day: Int, hour: Double): Double {
        val jd = julianDay(year, month, day, hour)
        val t = (jd - 2451545.0) / 36525.0
        val l = normalize(252.251 + 149472.6746 * t)
        val m = toRad(174.795 + 149472.5153 * t)
        val merLon = l + 23.440 * sin(m) + 2.995 * sin(2 * m) + 0.537 * sin(3 * m)
        val ayanamsa = 23.85 + (jd - 2415020.0) / 365.25 * 0.0136806
        return normalize(merLon - ayanamsa)
    }

    /**
     * Calculate Jupiter longitude (simplified)
     */
    fun getJupiterLongitude(year: Int, month: Int, day: Int, hour: Double): Double {
        val jd = julianDay(year, month, day, hour)
        val t = (jd - 2451545.0) / 36525.0
        val l = normalize(34.351 + 3034.9057 * t)
        val m = toRad(20.020 + 3034.6748 * t)
        val jupLon = l + 5.555 * sin(m) + 0.168 * sin(2 * m)
        val ayanamsa = 23.85 + (jd - 2415020.0) / 365.25 * 0.0136806
        return normalize(jupLon - ayanamsa)
    }

    /**
     * Calculate Venus longitude (simplified)
     */
    fun getVenusLongitude(year: Int, month: Int, day: Int, hour: Double): Double {
        val jd = julianDay(year, month, day, hour)
        val t = (jd - 2451545.0) / 36525.0
        val l = normalize(181.979 + 58517.8156 * t)
        val m = toRad(212.448 + 58517.8039 * t)
        val venLon = l + 0.7758 * sin(m) + 0.00033 * sin(2 * m)
        val ayanamsa = 23.85 + (jd - 2415020.0) / 365.25 * 0.0136806
        return normalize(venLon - ayanamsa)
    }

    /**
     * Calculate Saturn longitude (simplified)
     */
    fun getSaturnLongitude(year: Int, month: Int, day: Int, hour: Double): Double {
        val jd = julianDay(year, month, day, hour)
        val t = (jd - 2451545.0) / 36525.0
        val l = normalize(50.077 + 1222.1138 * t)
        val m = toRad(317.021 + 1221.5515 * t)
        val satLon = l + 6.406 * sin(m) + 0.317 * sin(2 * m)
        val ayanamsa = 23.85 + (jd - 2415020.0) / 365.25 * 0.0136806
        return normalize(satLon - ayanamsa)
    }

    /**
     * Calculate Rahu (True North Node) longitude
     */
    fun getRahuLongitude(year: Int, month: Int, day: Int, hour: Double): Double {
        val jd = julianDay(year, month, day, hour)
        val t = (jd - 2451545.0) / 36525.0
        val rahu = normalize(125.044522 - 1934.136261 * t)
        val ayanamsa = 23.85 + (jd - 2415020.0) / 365.25 * 0.0136806
        return normalize(rahu - ayanamsa)
    }

    /**
     * Ketu is exactly opposite to Rahu
     */
    fun getKetuLongitude(year: Int, month: Int, day: Int, hour: Double): Double {
        return normalize(getRahuLongitude(year, month, day, hour) + 180.0)
    }

    /**
     * Calculate Ascendant (Lagna) based on birth time and location
     */
    fun getAscendant(year: Int, month: Int, day: Int, hour: Double, latitude: Double, longitude: Double): Double {
        val jd = julianDay(year, month, day, hour - longitude / 15.0)
        val t = (jd - 2451545.0) / 36525.0
        // Local Sidereal Time
        val gmst = 280.46061837 + 360.98564736629 * (jd - 2451545.0) + 0.000387933 * t * t
        val lst = normalize(gmst + longitude)
        // Obliquity of ecliptic
        val eps = toRad(23.439291111 - 0.013004167 * t)
        val latRad = toRad(latitude)
        val lstRad = toRad(lst)
        val ascRad = atan2(cos(lstRad), -(sin(lstRad) * cos(eps) + tan(latRad) * sin(eps)))
        val asc = normalize(toDeg(ascRad))
        val ayanamsa = 23.85 + (jd - 2415020.0) / 365.25 * 0.0136806
        return normalize(asc - ayanamsa)
    }

    /**
     * Calculate all 12 houses using Placidus-like system (simplified equal house)
     */
    fun calculateHouses(ascendant: Double): List<Double> {
        return (0 until 12).map { i -> normalize(ascendant + i * 30.0) }
    }

    /**
     * Get all planetary positions as a map
     */
    fun getAllPlanetPositions(year: Int, month: Int, day: Int, hour: Double): Map<String, Double> {
        return mapOf(
            "Sun" to getSunLongitude(year, month, day, hour),
            "Moon" to getMoonLongitude(year, month, day, hour),
            "Mars" to getMarsLongitude(year, month, day, hour),
            "Mercury" to getMercuryLongitude(year, month, day, hour),
            "Jupiter" to getJupiterLongitude(year, month, day, hour),
            "Venus" to getVenusLongitude(year, month, day, hour),
            "Saturn" to getSaturnLongitude(year, month, day, hour),
            "Rahu" to getRahuLongitude(year, month, day, hour),
            "Ketu" to getKetuLongitude(year, month, day, hour)
        )
    }

    /**
     * Get degree within sign (0-30)
     */
    fun getDegreeInSign(longitude: Double): Double {
        return normalize(longitude) % 30.0
    }

    /**
     * Calculate compatibility score between two ascendants/moon signs (Kuta system)
     */
    fun calculateCompatibility(moonSign1: Int, moonSign2: Int, nakshatra1: Int, nakshatra2: Int): Int {
        var score = 0
        val max = 36

        // Varna (1 point)
        val varnas = intArrayOf(3, 2, 3, 1, 3, 2, 3, 1, 3, 2, 3, 1)
        if (varnas[moonSign1] >= varnas[moonSign2]) score += 1

        // Vashya (2 points) - simplified
        val diff = abs(moonSign1 - moonSign2)
        if (diff == 0) score += 2 else if (diff == 2 || diff == 10) score += 1

        // Tara (3 points)
        val taraDiff = (nakshatra2 - nakshatra1 + 27) % 27
        when {
            taraDiff % 9 == 0 -> score += 0
            taraDiff % 9 in 1..3 -> score += 3
            taraDiff % 9 in 4..6 -> score += 1
            else -> score += 2
        }

        // Yoni (4 points) - simplified
        val yoniGroups = intArrayOf(0, 1, 2, 3, 1, 4, 5, 6, 3, 7, 8, 2, 9, 10, 11, 9, 12, 6, 13, 8, 11, 14, 0, 5, 7, 4, 14)
        val yoni1 = if (nakshatra1 < 27) yoniGroups[nakshatra1] else 0
        val yoni2 = if (nakshatra2 < 27) yoniGroups[nakshatra2] else 0
        score += if (yoni1 == yoni2) 4 else if (abs(yoni1 - yoni2) <= 2) 2 else 0

        // Graha Maitri (5 points) - planet friendship
        val rulers = intArrayOf(2, 5, 3, 1, 0, 3, 5, 2, 4, 6, 6, 4)
        val r1 = rulers[moonSign1]
        val r2 = rulers[moonSign2]
        score += if (r1 == r2) 5 else if (abs(r1 - r2) <= 2) 3 else 1

        // Gana (6 points)
        val ganas = intArrayOf(0, 2, 0, 1, 0, 1, 0, 1, 2, 2, 0, 0, 1, 0, 1, 0, 1, 2, 2, 1, 0, 1, 0, 2, 1, 1, 1)
        val g1 = if (nakshatra1 < 27) ganas[nakshatra1] else 0
        val g2 = if (nakshatra2 < 27) ganas[nakshatra2] else 0
        score += if (g1 == g2) 6 else if (abs(g1 - g2) == 1) 3 else 0

        // Bhakut (7 points)
        val moonDiff = (moonSign2 - moonSign1 + 12) % 12
        score += when (moonDiff) {
            1, 2, 3, 4, 5, 7 -> 7
            0 -> 7
            else -> 0
        }

        // Nadi (8 points)
        val nadis = intArrayOf(0, 1, 2, 2, 1, 0, 0, 1, 2, 0, 1, 2, 2, 1, 0, 0, 1, 2, 2, 1, 0, 0, 1, 2, 0, 1, 2)
        val n1 = if (nakshatra1 < 27) nadis[nakshatra1] else 0
        val n2 = if (nakshatra2 < 27) nadis[nakshatra2] else 0
        score += if (n1 != n2) 8 else 0

        return minOf(score, max)
    }

    /**
     * Generate horoscope prediction text based on zodiac sign
     */
    fun generateHoroscope(zodiacIndex: Int, type: String): String {
        val predictions = listOf(
            // Aries
            listOf(
                "The stars shine brightly on you today, Aries! Your natural leadership abilities are at their peak. Take initiative in new ventures and trust your instincts. Love and relationships bring positive energy.",
                "This week brings opportunities for growth, Aries. Focus on your goals with determination. A financial matter resolves favorably. Partnerships strengthen through honest communication.",
                "March brings renewal to your ambitious soul, Aries. Career prospects brighten considerably. Health improves when you follow a disciplined routine. Love deepens with mutual understanding.",
                "2025 is a transformative year for you, Aries! Major career advancement is indicated. Relationships deepen and new meaningful connections form. Financial stability grows through wise investments."
            ),
            // Taurus
            listOf(
                "Stability and comfort surround you today, Taurus. Financial matters look favorable. Your practical nature helps you make sound decisions. Enjoy the simple pleasures of life.",
                "This week, Taurus, your perseverance pays off. A long-standing effort shows results. Home and family bring joy. Take time to appreciate your blessings and share them with loved ones.",
                "April's energies support your material goals, Taurus. Property matters resolve well. Your artistic talents find expression. Love relationships deepen with patience and tenderness.",
                "Your steady determination leads to remarkable achievements this year, Taurus. Financial security strengthens. A significant relationship milestone is reached. Health thrives with consistent care."
            ),
            // Gemini
            listOf(
                "Your quick mind is an asset today, Gemini! Communication flows effortlessly. New information comes your way that helps you make an important decision. Social interactions are lively and stimulating.",
                "This week brings mental stimulation, Gemini. New learning opportunities appear. Short travels are favorable. Your adaptability helps you navigate changing circumstances with ease and grace.",
                "May energizes your curious nature, Gemini. Writing, speaking, and teaching endeavors flourish. A sibling or neighbor plays an important role. Romance brings excitement and intellectual connection.",
                "This is a year of versatile achievements for you, Gemini! Multiple projects succeed simultaneously. Your network expands greatly. A new skill or qualification opens significant doors."
            ),
            // Cancer
            listOf(
                "Your intuition is your superpower today, Cancer. Family matters warm your heart. Home is your sanctuary and source of strength. Emotional connections deepen and bring fulfillment.",
                "This week emphasizes nurturing, Cancer. Care for yourself as much as you care for others. A domestic matter resolves peacefully. Your empathetic nature helps someone who needs your support.",
                "June's waters flow gently for you, Cancer. Home improvements bring satisfaction. Family celebrations are indicated. Creative expression through cooking, art, or music brings deep joy.",
                "Emotional growth and family expansion mark this year, Cancer! Real estate matters are favorable. Your nurturing nature attracts loyal relationships. Financial security increases through careful planning."
            ),
            // Leo
            listOf(
                "Your charisma shines brilliantly today, Leo! Recognition and appreciation come your way. Creative projects reach successful completion. Romance burns bright and passionate.",
                "This week puts you in the spotlight, Leo. Your leadership is called upon. A creative endeavor wins appreciation. Children or young people bring joy and inspiration to your life.",
                "July's sun empowers your regal nature, Leo. Professional achievements bring pride. Love and romance flourish dramatically. Your generosity creates lasting positive impressions.",
                "This is your year to roar, Leo! Career peaks are reached. Your creative talents earn widespread recognition. Love relationships reach new heights of passion and commitment."
            ),
            // Virgo
            listOf(
                "Your analytical mind solves a complex puzzle today, Virgo. Work efficiency is at its highest. Health improvements come through mindful habits. Service to others brings deep satisfaction.",
                "This week rewards your meticulous nature, Virgo. A project you've carefully prepared reaches fruition. Health matters respond well to your disciplined approach. Financial organization pays dividends.",
                "August's harvest energy supports your efforts, Virgo. Career recognition comes through consistent excellence. Health and wellness goals are achieved. Romantic relationships deepen through practical support.",
                "This year rewards your dedicated service, Virgo! Professional expertise is widely recognized. Health flourishes with your systematic approach. A meaningful relationship offers both love and friendship."
            ),
            // Libra
            listOf(
                "Balance and harmony surround you today, Libra. Relationships flourish with mutual understanding. Your diplomatic skills resolve a difficult situation. Beauty and art bring joy to your soul.",
                "This week brings harmony in partnerships, Libra. Collaborative efforts succeed beautifully. Legal or contractual matters resolve in your favor. Your sense of justice guides you to the right path.",
                "September's balanced energies support your relationships, Libra. Business and romantic partnerships thrive. Aesthetic pursuits bring both pleasure and profit. Important decisions are made with wisdom.",
                "Partnership and balance define this year for you, Libra! A significant commitment is made. Professional collaborations produce outstanding results. Financial growth comes through shared ventures."
            ),
            // Scorpio
            listOf(
                "Your transformative power is active today, Scorpio. Deep insights reveal hidden truths. Research and investigation yield significant discoveries. Psychological awareness helps you understand others.",
                "This week brings depth and intensity, Scorpio. Shared resources and finances improve. A transformative experience deepens your wisdom. Hidden matters come to light in a beneficial way.",
                "October's mysterious energy resonates with you, Scorpio. Occult studies and metaphysics fascinate and enlighten. Shared finances improve substantially. Deep romantic connections are formed or strengthened.",
                "This year brings powerful transformation, Scorpio! Career reinvention succeeds magnificently. Financial inheritances or investments yield excellent returns. A deep soul connection is formed or renewed."
            ),
            // Sagittarius
            listOf(
                "Adventure calls to your spirit today, Sagittarius! Higher learning expands your horizons. Travel opportunities appear on the horizon. Your philosophical outlook inspires those around you.",
                "This week broadens your perspective, Sagittarius. Educational pursuits advance significantly. A foreign connection proves valuable. Your optimism is infectious and attracts good fortune.",
                "November's expansive energy fires your enthusiasm, Sagittarius. International connections flourish. Publishing or teaching endeavors succeed. Philosophical insights guide you to your true purpose.",
                "Freedom and expansion characterize this year for you, Sagittarius! International opportunities open wide. Educational achievements are celebrated. A wise mentor helps you reach your highest potential."
            ),
            // Capricorn
            listOf(
                "Your ambitious nature reaches new heights today, Capricorn. Professional recognition comes for your dedicated efforts. Practical planning pays off. Your disciplined approach earns well-deserved respect.",
                "This week rewards your patient efforts, Capricorn. Career advancement is indicated. Authority figures support your goals. Long-term investments show promising returns.",
                "December's practical energies align with your nature, Capricorn. Professional achievements reach a peak. Reputation is enhanced through consistent excellence. Family traditions bring comfort and joy.",
                "This is a year of professional triumph, Capricorn! Career peaks and promotions are strongly indicated. Your disciplined efforts build lasting wealth. Social status rises with well-deserved recognition."
            ),
            // Aquarius
            listOf(
                "Innovation flows through your mind today, Aquarius! Humanitarian impulses guide your actions. Technology and new ideas bring breakthroughs. Social connections open unexpected opportunities.",
                "This week highlights your unique vision, Aquarius. Community involvement brings rewards. Technological pursuits advance your career. Friends support your unconventional but inspired approaches.",
                "January's revolutionary energy empowers your idealism, Aquarius. Social causes you champion gain momentum. Inventive solutions to longstanding problems emerge. Friendship circles expand meaningfully.",
                "This year fulfills your humanitarian vision, Aquarius! Social innovations you champion gain wide adoption. Technology-related endeavors flourish spectacularly. A unique opportunity changes your life direction."
            ),
            // Pisces
            listOf(
                "Your intuitive gifts are heightened today, Pisces. Creative and spiritual activities bring profound fulfillment. Compassionate service to others returns blessings to you. Dreams carry important messages.",
                "This week deepens your spiritual connection, Pisces. Artistic and musical endeavors bring joy. A retreat or quiet time restores your sensitive soul. Compassionate gestures create lasting positive karma.",
                "February's dreamy energies resonate with your soul, Pisces. Artistic projects reach successful completion. Spiritual practices deepen your wisdom. A past karma resolves peacefully and beautifully.",
                "This is a year of spiritual and creative flowering for you, Pisces! Artistic talents earn recognition and reward. Spiritual insights guide you to your true dharmic path. Compassionate service brings joy."
            )
        )

        val idx = zodiacIndex.coerceIn(0, 11)
        val typeIdx = when (type) {
            Constants.HOROSCOPE_DAILY -> 0
            Constants.HOROSCOPE_WEEKLY -> 1
            Constants.HOROSCOPE_MONTHLY -> 2
            Constants.HOROSCOPE_YEARLY -> 3
            else -> 0
        }
        return predictions[idx][typeIdx]
    }

    /**
     * Get lucky numbers for a zodiac sign
     */
    fun getLuckyNumbers(zodiacIndex: Int): List<Int> {
        val luckyNums = listOf(
            listOf(1, 9, 19), listOf(2, 6, 9), listOf(3, 5, 12), listOf(2, 7, 15),
            listOf(1, 5, 10), listOf(3, 5, 14), listOf(2, 7, 11), listOf(8, 9, 18),
            listOf(3, 9, 21), listOf(6, 8, 15), listOf(4, 7, 11), listOf(3, 7, 12)
        )
        return luckyNums.getOrElse(zodiacIndex) { listOf(1, 5, 9) }
    }

    /**
     * Get lucky colors for a zodiac sign
     */
    fun getLuckyColors(zodiacIndex: Int): List<String> {
        val colors = listOf(
            listOf("Red", "Scarlet", "White"), listOf("Green", "Pink", "White"),
            listOf("Yellow", "Green", "Blue"), listOf("White", "Silver", "Cream"),
            listOf("Gold", "Orange", "Purple"), listOf("Brown", "Grey", "Green"),
            listOf("Blue", "Pink", "Lavender"), listOf("Black", "Maroon", "Red"),
            listOf("Purple", "Blue", "Yellow"), listOf("Brown", "Black", "Dark Blue"),
            listOf("Electric Blue", "Grey", "Purple"), listOf("Sea Green", "Lavender", "White")
        )
        return colors.getOrElse(zodiacIndex) { listOf("White", "Blue", "Green") }
    }

    /**
     * Get planet remedies
     */
    fun getPlanetRemedies(planetIndex: Int): String {
        val remedies = listOf(
            "Offer water to the Sun at sunrise. Recite Aditya Hridaya Stotra. Wear ruby gemstone.",
            "Offer milk to Shiva on Mondays. Recite Chandra Mantra. Wear pearl or moonstone.",
            "Donate red items on Tuesdays. Recite Mangal mantra. Wear red coral gemstone.",
            "Worship Lord Vishnu on Wednesdays. Recite Budha mantra. Wear emerald gemstone.",
            "Worship Brihaspati on Thursdays. Recite Guru mantra. Wear yellow sapphire.",
            "Worship Goddess Lakshmi on Fridays. Recite Shukra mantra. Wear diamond or white sapphire.",
            "Worship Lord Shani on Saturdays. Recite Shani mantra. Wear blue sapphire carefully.",
            "Worship Durga and donate on Saturdays for Rahu. Wear hessonite garnet.",
            "Worship Ganesha for Ketu. Fast on Tuesdays. Wear cat's eye gemstone."
        )
        return remedies.getOrElse(planetIndex) { "Meditate daily for spiritual growth and inner peace." }
    }
}
