package com.example.recipeappproject.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeappproject.databinding.ItemStepBinding
import com.example.recipeappproject.ui.model.StepModel

class DetailRecipeAdapter: RecyclerView.Adapter<DetailRecipeAdapter.ItemStepViewHolder>() {

    var items: ArrayList<StepModel> = arrayListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemStepViewHolder {
        return ItemStepViewHolder(
            _binding = ItemStepBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemStepViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ItemStepViewHolder(
        private var _binding: ItemStepBinding
    ) : RecyclerView.ViewHolder(_binding.root) {

        fun bindItem(item: StepModel) {
            with(_binding) {
                tvNumber.text = item.number.toString()
                tvStep.text = item.step
            }

        }
    }
}