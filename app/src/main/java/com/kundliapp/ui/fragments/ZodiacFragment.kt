package com.kundliapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.kundliapp.databinding.FragmentZodiacBinding
import com.kundliapp.ui.activities.ZodiacDetailActivity
import com.kundliapp.ui.adapters.ZodiacAdapter
import com.kundliapp.utils.Constants

class ZodiacFragment : Fragment() {

    private var _binding: FragmentZodiacBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentZodiacBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ZodiacAdapter { index ->
            startActivity(Intent(requireContext(), ZodiacDetailActivity::class.java).apply {
                putExtra(Constants.KEY_ZODIAC_INDEX, index)
            })
        }

        binding.rvZodiac.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvZodiac.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
