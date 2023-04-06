package com.example.recipeappproject.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeappproject.databinding.ItemIngredientBinding
import com.example.recipeappproject.databinding.ItemRecipeBinding
import com.example.recipeappproject.ui.model.IngredientModel
import com.example.recipeappproject.ui.model.RecipeModel

class IngredientsAdapter: RecyclerView.Adapter<IngredientsAdapter.ItemIngredientViewHolder>() {

    var items: ArrayList<IngredientModel> = arrayListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemIngredientViewHolder {
        return ItemIngredientViewHolder(
            _binding = ItemIngredientBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemIngredientViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ItemIngredientViewHolder(
        private var _binding: ItemIngredientBinding
    ) : RecyclerView.ViewHolder(_binding.root) {

        fun bindItem(item: IngredientModel) {
            with(_binding) {
                tvNameIngredient.text = item.name
                tvUnit.text = item.unit
                tvValue.text = item.value.toString()
            }

        }
    }
}