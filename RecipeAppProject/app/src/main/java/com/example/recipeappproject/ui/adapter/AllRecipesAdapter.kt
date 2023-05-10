package com.example.recipeappproject.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeappproject.databinding.ItemRecipeBinding
import com.example.recipeappproject.ui.model.RecipeModel

class AllRecipesAdapter: RecyclerView.Adapter<AllRecipesAdapter.ItemAllRecipesViewHolder>() {

    var onItemClickListener: ((RecipeModel) -> Unit)? = null

    var items: ArrayList<RecipeModel> = arrayListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAllRecipesViewHolder {
        return ItemAllRecipesViewHolder(
            _binding = ItemRecipeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemAllRecipesViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ItemAllRecipesViewHolder(
        private var _binding: ItemRecipeBinding
    ) : RecyclerView.ViewHolder(_binding.root) {

        init {
            with (_binding) {
                root.setOnClickListener {
                    onItemClickListener?.invoke(items[adapterPosition])
                    notifyItemChanged(adapterPosition)
                }
                llItemRecipe.setOnClickListener {
                    onItemClickListener?.invoke(items[adapterPosition])

                }
            }
        }

        fun bindItem(item: RecipeModel) {
            with(_binding) {
                tvNameRecipe.text = item.title
                Glide.with(ivRecipe.context)
                    .load(item.image)
                    .into(ivRecipe)
            }

        }
    }
}