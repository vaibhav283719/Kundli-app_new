package com.kundliapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kundliapp.databinding.FragmentMoreBinding
import com.kundliapp.ui.activities.CompatibilityActivity

class MoreFragment : Fragment() {

    private var _binding: FragmentMoreBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cardCompatibility.setOnClickListener {
            startActivity(Intent(requireContext(), CompatibilityActivity::class.java))
        }

        binding.cardNakshatras.setOnClickListener {
            // Show nakshatras list
            showNakshatrasList()
        }

        binding.cardHouses.setOnClickListener {
            // Show houses info
            showHousesInfo()
        }
    }

    private fun showNakshatrasList() {
        // Navigate to nakshatras screen
    }

    private fun showHousesInfo() {
        // Navigate to houses screen
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
