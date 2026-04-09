package com.kundliapp.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kundliapp.R
import com.kundliapp.databinding.ActivityZodiacDetailBinding
import com.kundliapp.utils.Constants

class ZodiacDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityZodiacDetailBinding

    private val zodiacTraits = listOf(
        "Aries are bold, ambitious, and energetic. They are natural leaders who love to initiate and are always ready for a challenge. Ruled by Mars, they are courageous and determined.",
        "Taurus are reliable, patient, and practical. They value security and comfort, and have a strong appreciation for beauty and luxury. Ruled by Venus, they are loyal and steadfast.",
        "Gemini are versatile, curious, and communicative. They are quick-witted and love to learn and share knowledge. Ruled by Mercury, they adapt easily to new situations.",
        "Cancer are intuitive, emotional, and nurturing. They are deeply connected to home and family, with strong psychic abilities. Ruled by the Moon, they are protective and caring.",
        "Leo are dramatic, creative, and self-confident. They love to be in the spotlight and are generous with their warmth. Ruled by the Sun, they are natural performers and leaders.",
        "Virgo are analytical, practical, and diligent. They pay attention to detail and have a strong desire to help others. Ruled by Mercury, they excel at problem-solving.",
        "Libra are diplomatic, fair-minded, and social. They seek balance and harmony in all aspects of life. Ruled by Venus, they have a love for beauty and partnership.",
        "Scorpio are passionate, resourceful, and determined. They possess great depth and emotional intelligence. Ruled by Pluto/Mars, they are transformative and powerful.",
        "Sagittarius are optimistic, adventurous, and philosophical. They love freedom and exploring new ideas. Ruled by Jupiter, they are expansive and enthusiastic.",
        "Capricorn are disciplined, responsible, and ambitious. They are patient and persistent in achieving their goals. Ruled by Saturn, they are practical and hardworking.",
        "Aquarius are progressive, original, and humanitarian. They are forward-thinking and love intellectual stimulation. Ruled by Uranus/Saturn, they are innovative.",
        "Pisces are compassionate, artistic, and intuitive. They are deeply empathetic and have rich inner lives. Ruled by Neptune/Jupiter, they are spiritual and creative."
    )

    private val zodiacCompatibility = listOf(
        "Best with: Leo, Sagittarius, Gemini, Aquarius\nChallenging with: Cancer, Capricorn",
        "Best with: Virgo, Capricorn, Cancer, Pisces\nChallenging with: Leo, Aquarius",
        "Best with: Libra, Aquarius, Aries, Leo\nChallenging with: Virgo, Pisces",
        "Best with: Scorpio, Pisces, Taurus, Virgo\nChallenging with: Aries, Libra",
        "Best with: Aries, Sagittarius, Gemini, Libra\nChallenging with: Taurus, Scorpio",
        "Best with: Taurus, Capricorn, Cancer, Scorpio\nChallenging with: Gemini, Sagittarius",
        "Best with: Gemini, Aquarius, Leo, Sagittarius\nChallenging with: Cancer, Capricorn",
        "Best with: Cancer, Pisces, Virgo, Capricorn\nChallenging with: Leo, Aquarius",
        "Best with: Aries, Leo, Gemini, Aquarius\nChallenging with: Virgo, Pisces",
        "Best with: Taurus, Virgo, Scorpio, Pisces\nChallenging with: Aries, Libra",
        "Best with: Gemini, Libra, Aries, Sagittarius\nChallenging with: Taurus, Scorpio",
        "Best with: Cancer, Scorpio, Taurus, Capricorn\nChallenging with: Gemini, Sagittarius"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityZodiacDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val index = intent.getIntExtra(Constants.KEY_ZODIAC_INDEX, 0).coerceIn(0, 11)

        supportActionBar?.title = Constants.ZODIAC_SIGNS[index]

        binding.tvSymbol.text = Constants.ZODIAC_SYMBOLS[index]
        binding.tvName.text = Constants.ZODIAC_SIGNS[index]
        binding.tvElement.text = "Element: ${Constants.ZODIAC_ELEMENTS[index]}"
        binding.tvRulingPlanet.text = "Ruling Planet: ${Constants.ZODIAC_RULING_PLANETS[index]}"
        binding.tvTraits.text = zodiacTraits[index]
        binding.tvCompatibility.text = zodiacCompatibility[index]
        binding.tvLuckyNumbers.text = "Lucky Numbers: ${Constants.ZODIAC_SIGNS[index].let {
            com.kundliapp.utils.AstrologicalCalculations.getLuckyNumbers(index).joinToString(", ")
        }}"
        binding.tvLuckyColors.text = "Lucky Colors: ${
            com.kundliapp.utils.AstrologicalCalculations.getLuckyColors(index).joinToString(", ")
        }"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
