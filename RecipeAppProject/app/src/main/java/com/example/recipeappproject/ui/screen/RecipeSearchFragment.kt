package com.example.recipeappproject.ui.screen

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeappproject.R
import com.example.recipeappproject.databinding.FragmentRecipeSearchBinding
import com.example.recipeappproject.di.DataDependency
import com.example.recipeappproject.di.ViewModelArgsKeys
import com.example.recipeappproject.ui.adapter.AllRecipesAdapter
import com.example.recipeappproject.ui.model.RecipeModel
import com.example.recipeappproject.ui.mvvm.RecipeSearchFragmentViewModel
import retrofit2.HttpException


class RecipeSearchFragment: Fragment(R.layout.fragment_recipe_search) {

    private lateinit var binding: FragmentRecipeSearchBinding
    private var rvRecipesAdapter = AllRecipesAdapter()
    private val viewModel: RecipeSearchFragmentViewModel by viewModels(extrasProducer = {
        MutableCreationExtras().apply {
            set(ViewModelArgsKeys.getRecipesByNameCaseKey, DataDependency.getRecipesByNameUseCase)
        }
    }) {
        RecipeSearchFragmentViewModel.factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        rvRecipesAdapter.onItemClickListener = { itemData ->
            val bundle = Bundle()
            bundle.putString(KEY_LAST_FRAGMENT, "search")
            bundle.putLong(KEY_ID_INGREDIENT, itemData.id)
            bundle.putString(KEY_NAME_INGREDIENT, itemData.title)
            bundle.putString(KEY_IMAGE_INGREDIENT, itemData.image)
            findNavController().navigate(
                R.id.action_recipeSearchFragment_to_detailRecipeFragment,
                bundle
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRecipeSearchBinding.bind(view)
        viewModel.data.value?.let { data ->
            updateItems(data.recipes as ArrayList<RecipeModel>)
        }
        with(binding) {
            rvRecipes.adapter = rvRecipesAdapter
            rvRecipes.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
        initViews()
    }

    private fun updateItems(listRecipes: ArrayList<RecipeModel>) {
        rvRecipesAdapter.items = listRecipes
    }

    private fun initViews() {
        with(binding) {
            tvNotFound.isVisible = false
            ivSearch.setOnClickListener {
                if (etSearchRecipe.text.toString()  != "") {
                    if (isOnline()) {
                        observeData(etSearchRecipe.text.toString(), spinnerDiet.selectedItem.toString())
                    } else {
                        rvRecipes.isVisible = false
                        tvNotFound.text = MESSAGE_NO_CONNECT
                        tvNotFound.isVisible = true
                    }
                }
            }
        }
    }

    private fun observeData(recipeName: String?, diet: String) {
        binding.tvNotFound.isVisible = false
        val listRecipes: ArrayList<RecipeModel> = arrayListOf()
        viewModel.requestRecipesByName(recipeName.toString(), diet)
        viewModel.progressBarState.observe(viewLifecycleOwner) { isVisible ->
            with(binding) {
                progressBar.isVisible = isVisible
                rvRecipes.isVisible = !isVisible
            }
        }
        viewModel.recipeDataState.observe(viewLifecycleOwner) { recipesDataModel ->
            recipesDataModel?.let { data ->
                val result = data.recipes
                listRecipes.clear()
                for (i in result.indices) {
                    listRecipes.add(
                        RecipeModel(
                            result[i].id,
                            result[i].title,
                            result[i].image,
                            result[i].imageType
                        )
                    )
                }
                with(binding) {
                    if (listRecipes.isEmpty()) {
                        tvNotFound.text = MESSAGE_NOT_FOUND
                        tvNotFound.isVisible = true
                        rvRecipes.isVisible = false
                    } else {
                        tvNotFound.isVisible = false
                        updateItems(listRecipes)
                    }
                }
            }

        }
        viewModel.errorState.observe(viewLifecycleOwner) { ex ->
            ex?.let {
                val errorMessage = (ex as? HttpException)?.message ?: ex.toString()
                Toast.makeText(
                    requireContext(),
                    errorMessage,
                    Toast.LENGTH_SHORT
                ).show()
                Log.i("ERR", errorMessage)
            }
        }
    }

    private fun isOnline(): Boolean {
        val cm = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

    override fun onDestroyView() {
        super.onDestroyView()
        println("TEST TAG - RecipesSearchFragment onDestroy")
    }

    override fun onResume() {
        super.onResume()
        println("TEST TAG - RecipesSearchFragment onResume")
    }

    override fun onPause() {
        super.onPause()
        println("TEST TAG - RecipesSearchFragment onPause")
    }

    companion object {

        const val KEY_LAST_FRAGMENT = "key-last-frag"
        const val KEY_ID_INGREDIENT = "key-id-ingredient"
        const val KEY_NAME_INGREDIENT = "key-name-ingredient"
        const val KEY_IMAGE_INGREDIENT = "key-image-ingredient"

        const val MESSAGE_NO_CONNECT = "No internet connection."
        const val MESSAGE_NOT_FOUND = "No recipes found for your request :("
    }
}