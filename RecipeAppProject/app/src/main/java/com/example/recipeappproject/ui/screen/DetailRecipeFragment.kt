package com.example.recipeappproject.ui.screen

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
import com.bumptech.glide.Glide
import com.example.recipeappproject.R
import com.example.recipeappproject.databinding.FragmentDetailRecipeBinding
import com.example.recipeappproject.databinding.FragmentRecipeSearchBinding
import com.example.recipeappproject.di.DataDependency
import com.example.recipeappproject.di.ViewModelArgsKeys
import com.example.recipeappproject.ui.adapter.AllRecipesAdapter
import com.example.recipeappproject.ui.adapter.IngredientsAdapter
import com.example.recipeappproject.ui.model.IngredientModel
import com.example.recipeappproject.ui.model.RecipeModel
import com.example.recipeappproject.ui.mvvm.DetailRecipeFragmentViewModel
import com.example.recipeappproject.ui.mvvm.RecipeSearchFragmentViewModel
import retrofit2.HttpException

class DetailRecipeFragment: Fragment(R.layout.fragment_detail_recipe) {

    private var _binding: FragmentDetailRecipeBinding? = null
    private val binding get() = _binding!!
    private var rvIngredientsAdapter: IngredientsAdapter? = null
    private var listIngredients: ArrayList<IngredientModel> = arrayListOf()
    private var idIng: Long? = null
    private var nameIng: String? = null
    private var imageIng: String? = null
    private val viewModel: DetailRecipeFragmentViewModel by viewModels(extrasProducer = {
        MutableCreationExtras().apply {
            set(ViewModelArgsKeys.getIngredientsByIdCaseKey, DataDependency.getIngredientsByIdUseCase)
        }
    }) {
        DetailRecipeFragmentViewModel.factory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailRecipeBinding.bind(view)

        initViews()
        idIng = arguments?.getLong("key-id-ingredient")
        nameIng = arguments?.getString("key-name-ingredient")
        imageIng = arguments?.getString("key-image-ingredient")
        idIng?.let { observeData(it) }
    }

    private fun initViews() {
        with(binding) {
            llSearch.setOnClickListener {
                findNavController().navigate(
                    R.id.action_detailRecipeFragment_to_recipeSearchFragment
                )
            }
        }
    }

    private fun observeData(id: Long) {
        viewModel.requestIngredientsById(id)
        viewModel.progressBarState.observe(viewLifecycleOwner) { isVisible ->
            binding.progressBar.isVisible = isVisible
        }
        viewModel.viewsState.observe(viewLifecycleOwner) { isVisible ->
            with(binding) {
                tvNameRecipe.isVisible = isVisible
                tvRecipe.isVisible = isVisible
                tvIngredients.isVisible = isVisible
                ivRecipe.isVisible = isVisible
            }
        }
        viewModel.ingredientDataState.observe(viewLifecycleOwner) { ingredientDataModel ->
            ingredientDataModel?.let { data ->
                with(binding) {
                    tvNameRecipe.text = nameIng
                    Glide.with(requireContext())
                        .load(imageIng)
                        .into(ivRecipe)
                    listIngredients.clear()
                    val result = data.ingredients
                    for (i in result.indices) {
                        listIngredients.add(
                            IngredientModel(
                                result[i].unit,
                                result[i].value,
                                result[i].name
                            )
                        )
                    }
                }
                if (listIngredients.isNullOrEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Not found ingredients :(",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    initAdapter()
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

    private fun initAdapter() {
        rvIngredientsAdapter = IngredientsAdapter().apply {
            items = listIngredients
        }
        with(binding) {
            rvRecipes.adapter = rvIngredientsAdapter
            rvRecipes.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
        println("TEST TAG - DetailRecipeFragment onDestroy")
    }

    override fun onResume() {
        super.onResume()
        println("TEST TAG - DetailRecipeFragment onResume")
    }

    override fun onPause() {
        super.onPause()
        println("TEST TAG - DetailRecipeFragment onPause")
    }
}