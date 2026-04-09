package com.kundliapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kundliapp.data.database.entities.BirthProfile
import com.kundliapp.databinding.ItemProfileBinding
import com.kundliapp.utils.DateTimeUtils

class ProfileAdapter(
    private val onProfileClick: (BirthProfile) -> Unit,
    private val onDeleteClick: (BirthProfile) -> Unit
) : ListAdapter<BirthProfile, ProfileAdapter.ProfileViewHolder>(ProfileDiffCallback()) {

    inner class ProfileViewHolder(private val binding: ItemProfileBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(profile: BirthProfile) {
            binding.tvName.text = profile.name
            binding.tvSunSign.text = "☀ ${profile.sunSign}"
            binding.tvMoonSign.text = "☽ ${profile.moonSign}"
            binding.tvAscendant.text = "↑ ${profile.ascendant}"
            binding.tvBirthInfo.text = "${DateTimeUtils.formatDate(profile.birthYear, profile.birthMonth, profile.birthDay)} · ${profile.birthPlace}"
            binding.root.setOnClickListener { onProfileClick(profile) }
            binding.btnDelete.setOnClickListener { onDeleteClick(profile) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val binding = ItemProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ProfileDiffCallback : DiffUtil.ItemCallback<BirthProfile>() {
        override fun areItemsTheSame(oldItem: BirthProfile, newItem: BirthProfile) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: BirthProfile, newItem: BirthProfile) = oldItem == newItem
    }
}
