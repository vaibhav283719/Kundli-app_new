package com.kundliapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kundliapp.databinding.FragmentHomeBinding
import com.kundliapp.ui.activities.BirthChartActivity
import com.kundliapp.ui.activities.CompatibilityActivity
import com.kundliapp.ui.activities.HoroscopeActivity
import com.kundliapp.utils.Constants
import com.kundliapp.utils.DateTimeUtils

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendar = java.util.Calendar.getInstance()
        val monthNames = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
        val dayNames = listOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")

        binding.tvDate.text = "${dayNames[calendar.get(java.util.Calendar.DAY_OF_WEEK) - 1]}, " +
                "${calendar.get(java.util.Calendar.DAY_OF_MONTH)} " +
                "${monthNames[calendar.get(java.util.Calendar.MONTH)]} " +
                "${calendar.get(java.util.Calendar.YEAR)}"

        binding.cardBirthChart.setOnClickListener {
            startActivity(Intent(requireContext(), BirthChartActivity::class.java))
        }

        binding.cardHoroscope.setOnClickListener {
            startActivity(Intent(requireContext(), HoroscopeActivity::class.java).apply {
                putExtra(Constants.KEY_ZODIAC_INDEX, 0)
            })
        }

        binding.cardCompatibility.setOnClickListener {
            startActivity(Intent(requireContext(), CompatibilityActivity::class.java))
        }

        // Zodiac quick access
        val zodiacClicks = listOf(
            binding.cardAries, binding.cardTaurus, binding.cardGemini,
            binding.cardCancer, binding.cardLeo, binding.cardVirgo,
            binding.cardLibra, binding.cardScorpio, binding.cardSagittarius,
            binding.cardCapricorn, binding.cardAquarius, binding.cardPisces
        )
        zodiacClicks.forEachIndexed { index, card ->
            card.setOnClickListener {
                startActivity(Intent(requireContext(), HoroscopeActivity::class.java).apply {
                    putExtra(Constants.KEY_ZODIAC_INDEX, index)
                })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
