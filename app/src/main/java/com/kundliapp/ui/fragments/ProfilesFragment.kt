package com.kundliapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kundliapp.databinding.FragmentProfilesBinding
import com.kundliapp.ui.activities.BirthChartActivity
import com.kundliapp.ui.adapters.ProfileAdapter
import com.kundliapp.ui.viewmodels.BirthChartViewModel

class ProfilesFragment : Fragment() {

    private var _binding: FragmentProfilesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BirthChartViewModel by viewModels()
    private lateinit var adapter: ProfileAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfilesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ProfileAdapter(
            onProfileClick = { profile ->
                // Could navigate to profile detail view
            },
            onDeleteClick = { profile ->
                viewModel.deleteProfile(profile)
            }
        )

        binding.rvProfiles.layoutManager = LinearLayoutManager(requireContext())
        binding.rvProfiles.adapter = adapter

        binding.fabAddProfile.setOnClickListener {
            startActivity(Intent(requireContext(), BirthChartActivity::class.java))
        }

        viewModel.allProfiles.observe(viewLifecycleOwner) { profiles ->
            adapter.submitList(profiles)
            binding.tvEmpty.visibility = if (profiles.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
