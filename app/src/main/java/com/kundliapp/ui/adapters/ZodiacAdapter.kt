package com.kundliapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kundliapp.databinding.ItemZodiacBinding
import com.kundliapp.utils.Constants

class ZodiacAdapter(
    private val onZodiacClick: (Int) -> Unit
) : RecyclerView.Adapter<ZodiacAdapter.ZodiacViewHolder>() {

    inner class ZodiacViewHolder(private val binding: ItemZodiacBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(index: Int) {
            binding.tvSymbol.text = Constants.ZODIAC_SYMBOLS[index]
            binding.tvName.text = Constants.ZODIAC_SIGNS[index]
            binding.tvElement.text = Constants.ZODIAC_ELEMENTS[index]
            binding.root.setOnClickListener { onZodiacClick(index) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZodiacViewHolder {
        val binding = ItemZodiacBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ZodiacViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ZodiacViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = 12
}
