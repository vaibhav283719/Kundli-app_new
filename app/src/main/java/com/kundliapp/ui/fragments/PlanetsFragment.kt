package com.kundliapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kundliapp.databinding.FragmentPlanetsBinding
import com.kundliapp.databinding.ItemPlanetBinding
import com.kundliapp.ui.activities.PlanetDetailActivity
import com.kundliapp.utils.Constants

class PlanetsFragment : Fragment() {

    private var _binding: FragmentPlanetsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPlanetsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val planetEmojis = listOf("☀", "☽", "♂", "☿", "♃", "♀", "♄", "☊", "☋")
        val planetKeywords = listOf(
            "Soul, Vitality, Authority",
            "Mind, Emotions, Mother",
            "Energy, Courage, Action",
            "Intellect, Communication",
            "Wisdom, Expansion, Fortune",
            "Love, Beauty, Harmony",
            "Discipline, Karma, Structure",
            "Desire, Ambition, Future",
            "Spirituality, Liberation, Past"
        )

        binding.rvPlanets.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPlanets.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                val b = ItemPlanetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return object : RecyclerView.ViewHolder(b.root) {}
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                val b = ItemPlanetBinding.bind(holder.itemView)
                b.tvSymbol.text = planetEmojis[position]
                b.tvName.text = Constants.PLANETS[position]
                b.tvKeywords.text = planetKeywords[position]
                b.root.setOnClickListener {
                    startActivity(Intent(requireContext(), PlanetDetailActivity::class.java).apply {
                        putExtra(Constants.KEY_PLANET_INDEX, position)
                    })
                }
            }

            override fun getItemCount() = Constants.PLANETS.size
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
