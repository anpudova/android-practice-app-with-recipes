package com.example.recipeappproject.ui.screen

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeappproject.R
import com.example.recipeappproject.bd.DatabaseHandler
import com.example.recipeappproject.bd.model.FavoriteRecipeModel
import com.example.recipeappproject.bd.model.UserModel
import com.example.recipeappproject.databinding.FragmentFavoriteRecipeBinding
import com.example.recipeappproject.ui.adapter.AllRecipesAdapter
import com.example.recipeappproject.ui.model.RecipeModel
import kotlinx.coroutines.launch

class FavoriteRecipeFragment: Fragment(R.layout.fragment_favorite_recipe) {

    private lateinit var binding: FragmentFavoriteRecipeBinding
    private var rvRecipesAdapter: AllRecipesAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoriteRecipeBinding.bind(view)

        val preferences: SharedPreferences = requireActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE)
        val editPreferences: SharedPreferences.Editor = preferences.edit()
        var recipes: List<FavoriteRecipeModel>?
        lifecycleScope.launch {
            recipes = DatabaseHandler.getFavoriteRecipes(preferences.getLong("id", 0))
            val list = mutableListOf<RecipeModel>()
            recipes?.let {
                for (i in it.indices) {
                    list.add(
                        RecipeModel(
                            it[i].id,
                            it[i].name,
                            it[i].image,
                            ""
                        )
                    )
                }
                initAdapter(list as ArrayList<RecipeModel>)
            }
        }
    }

    private fun initAdapter(listRecipes: ArrayList<RecipeModel>) {
        rvRecipesAdapter = AllRecipesAdapter().apply {
            items = listRecipes
            onItemClickListener = { itemData ->
                val bundle = Bundle()
                bundle.putString("key-last-frag", "favorite")
                bundle.putLong("key-id-ingredient", itemData.id)
                bundle.putString("key-name-ingredient", itemData.title)
                bundle.putString("key-image-ingredient", itemData.image)
                findNavController().navigate(
                    R.id.action_favoriteRecipeFragment_to_detailRecipeFragment,
                    bundle
                )
            }
        }
        with(binding) {
            rvRecipes.adapter = rvRecipesAdapter
            rvRecipes.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        println("TEST TAG - FavoriteRecipeFragment onDestroy")
    }

    override fun onResume() {
        super.onResume()
        println("TEST TAG - FavoriteRecipeFragment onResume")
    }

    override fun onPause() {
        super.onPause()
        println("TEST TAG - FavoriteRecipeFragment onPause")
    }
}