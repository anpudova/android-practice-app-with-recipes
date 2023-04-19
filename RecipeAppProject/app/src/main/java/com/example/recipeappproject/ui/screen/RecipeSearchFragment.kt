package com.example.recipeappproject.ui.screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
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
    private var rvRecipesAdapter: AllRecipesAdapter? = null
    private val viewModel: RecipeSearchFragmentViewModel by viewModels(extrasProducer = {
        MutableCreationExtras().apply {
            set(ViewModelArgsKeys.getRecipesByNameCaseKey, DataDependency.getRecipesByNameUseCase)
        }
    }) {
        RecipeSearchFragmentViewModel.factory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRecipeSearchBinding.bind(view)
        viewModel.data.value?.let { data ->
            val result = data.recipes
            val listRecipes: ArrayList<RecipeModel> = arrayListOf()
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
            initAdapter(listRecipes)
            //то что выше не работает, видимо что-то делаю не так (список затирается когда возвращаюсь)
        }
        initViews()
    }

    private fun initAdapter(listRecipes: ArrayList<RecipeModel>) {
        rvRecipesAdapter = AllRecipesAdapter().apply {
            items = listRecipes
            onItemClickListener = { itemData ->
                val bundle = Bundle()
                bundle.putString("key-last-frag", "search")
                bundle.putLong("key-id-ingredient", itemData.id)
                bundle.putString("key-name-ingredient", itemData.title)
                bundle.putString("key-image-ingredient", itemData.image)
                findNavController().navigate(
                    R.id.action_recipeSearchFragment_to_detailRecipeFragment,
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

    private fun initViews() {
        with(binding) {
            ivSearch.setOnClickListener {
                if (etSearchRecipe.text.toString()  != "") {
                    observeData(etSearchRecipe.text.toString())
                }
            }
        }
    }

    private fun observeData(recipeName: String?) {
        viewModel.requestRecipesByName(recipeName.toString())
        viewModel.progressBarState.observe(viewLifecycleOwner) { isVisible ->
            binding.progressBar.isVisible = isVisible
        }
        viewModel.recipeDataState.observe(viewLifecycleOwner) { recipesDataModel ->
            val listRecipes: ArrayList<RecipeModel> = arrayListOf()
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
                if (listRecipes.isNullOrEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Not found recipe :(",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    initAdapter(listRecipes)
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
}