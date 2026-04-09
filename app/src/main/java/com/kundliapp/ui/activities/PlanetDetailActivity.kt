package com.kundliapp.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kundliapp.R
import com.kundliapp.databinding.ActivityPlanetDetailBinding
import com.kundliapp.utils.AstrologicalCalculations
import com.kundliapp.utils.Constants

class PlanetDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlanetDetailBinding

    private val planetDescriptions = listOf(
        "The Sun represents the soul, vitality, and self-expression. It rules the sign of Leo and is exalted in Aries. The Sun governs our conscious identity, ego, and the father. It represents authority, power, and leadership.",
        "The Moon represents the mind, emotions, and mother. It rules Cancer and is exalted in Taurus. The Moon governs our subconscious, habits, intuition, and emotional needs. It changes signs every 2.5 days.",
        "Mars represents energy, action, and desire. It rules Aries and Scorpio, and is exalted in Capricorn. Mars governs our drive, ambition, courage, and sexuality. It represents the warrior energy.",
        "Mercury represents communication, intellect, and learning. It rules Gemini and Virgo, and is exalted in Virgo. Mercury governs our thinking processes, communication style, and adaptability.",
        "Jupiter represents wisdom, expansion, and good fortune. It rules Sagittarius and Pisces, and is exalted in Cancer. Jupiter governs higher learning, philosophy, religion, and abundance.",
        "Venus represents love, beauty, and harmony. It rules Taurus and Libra, and is exalted in Pisces. Venus governs our values, aesthetic sense, relationships, and what we find pleasurable.",
        "Saturn represents discipline, karma, and structure. It rules Capricorn and Aquarius, and is exalted in Libra. Saturn governs our limitations, responsibilities, and life lessons.",
        "Rahu (North Node) represents worldly desires, ambitions, and future karma. It is a shadow planet that causes eclipses and represents the direction of karmic growth and ambition.",
        "Ketu (South Node) represents spirituality, past karma, and liberation. It is the tail of the dragon that points toward what we've already mastered and where we seek moksha."
    )

    private val planetHouses = listOf(
        "Rules: 5th House (Leo)\nExalted: 1st House (Aries)\nDebilitated: 7th House (Libra)",
        "Rules: 4th House (Cancer)\nExalted: 2nd House (Taurus)\nDebilitated: 8th House (Scorpio)",
        "Rules: 1st & 8th House\nExalted: 10th House (Capricorn)\nDebilitated: 4th House (Cancer)",
        "Rules: 3rd & 6th House\nExalted: 6th House (Virgo)\nDebilitated: 12th House (Pisces)",
        "Rules: 9th & 12th House\nExalted: 4th House (Cancer)\nDebilitated: 10th House (Capricorn)",
        "Rules: 2nd & 7th House\nExalted: 12th House (Pisces)\nDebilitated: 6th House (Virgo)",
        "Rules: 10th & 11th House\nExalted: 7th House (Libra)\nDebilitated: 1st House (Aries)",
        "Exalted: Gemini/Virgo\nDebilitated: Sagittarius/Pisces",
        "Exalted: Sagittarius/Pisces\nDebilitated: Gemini/Virgo"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlanetDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val index = intent.getIntExtra(Constants.KEY_PLANET_INDEX, 0).coerceIn(0, 8)
        val planetName = Constants.PLANETS[index]

        supportActionBar?.title = planetName

        binding.tvPlanetName.text = planetName
        binding.tvDescription.text = planetDescriptions[index]
        binding.tvHouseInfo.text = planetHouses[index]
        binding.tvRemedies.text = AstrologicalCalculations.getPlanetRemedies(index)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
