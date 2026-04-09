package com.kundliapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kundliapp.domain.models.PlanetPosition
import com.kundliapp.databinding.ItemPlanetPositionBinding

class PlanetPositionAdapter(
    private val positions: List<PlanetPosition>
) : RecyclerView.Adapter<PlanetPositionAdapter.PlanetViewHolder>() {

    inner class PlanetViewHolder(private val binding: ItemPlanetPositionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: PlanetPosition) {
            binding.tvPlanet.text = position.planet
            binding.tvSign.text = position.sign
            binding.tvDegree.text = String.format("%.2f°", position.degree)
            binding.tvHouse.text = "House ${position.house}"
            binding.tvNakshatra.text = position.nakshatra
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanetViewHolder {
        val binding = ItemPlanetPositionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlanetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlanetViewHolder, position: Int) {
        holder.bind(positions[position])
    }

    override fun getItemCount() = positions.size
}
