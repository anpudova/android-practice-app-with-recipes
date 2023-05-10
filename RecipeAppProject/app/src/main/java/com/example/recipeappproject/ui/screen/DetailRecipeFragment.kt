package com.example.recipeappproject.ui.screen

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeappproject.R
import com.example.recipeappproject.bd.DatabaseHandler
import com.example.recipeappproject.bd.model.FavoriteRecipeModel
import com.example.recipeappproject.databinding.FragmentDetailRecipeBinding
import com.example.recipeappproject.di.DataDependency
import com.example.recipeappproject.di.ViewModelArgsKeys
import com.example.recipeappproject.ui.adapter.DetailRecipeAdapter
import com.example.recipeappproject.ui.adapter.IngredientsAdapter
import com.example.recipeappproject.ui.model.IngredientModel
import com.example.recipeappproject.ui.model.StepModel
import com.example.recipeappproject.ui.mvvm.DetailRecipeFragmentViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException

class DetailRecipeFragment: Fragment(R.layout.fragment_detail_recipe) {

    private lateinit var binding: FragmentDetailRecipeBinding
    private val viewModel: DetailRecipeFragmentViewModel by viewModels(extrasProducer = {
        MutableCreationExtras().apply {
            set(ViewModelArgsKeys.getIngredientsByIdCaseKey, DataDependency.getIngredientsByIdUseCase)
            set(ViewModelArgsKeys.getDetailRecipeByIdCaseKey, DataDependency.getDetailRecipeByIdUseCase)
        }
    }) {
        DetailRecipeFragmentViewModel.factory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailRecipeBinding.bind(view)
        val last =  arguments?.getString("key-last-frag")
        val idIng = arguments?.getLong("key-id-ingredient")
        val nameIng = arguments?.getString("key-name-ingredient")
        val imageIng = arguments?.getString("key-image-ingredient")
        initViews(last, idIng, nameIng, imageIng)
        idIng?.let { id ->
            viewModel.requestIngredientsById(id)
            observeDataIngredients(nameIng, imageIng)
            viewModel.requestDetailRecipeById(id)
            observeDataDetails()
        }
    }

    private fun initViews(lastFragment: String?, id: Long?, name: String?, image: String?) {
        with(binding) {
            lifecycleScope.launch {
                id?.let { id ->
                    if (DatabaseHandler.existInFavorites(id) == 1) {
                        ivAddFavorite.setImageResource(R.drawable.favorite_icon)
                    } else {
                        ivAddFavorite.setImageResource(R.drawable.not_favorite_icon)
                    }
                }
            }
            ivBack.setOnClickListener {
                lastFragment?.let {
                    when(lastFragment){
                        "search" ->
                            findNavController().navigate(
                                R.id.action_detailRecipeFragment_to_recipeSearchFragment
                            )
                        "favorite" ->
                            findNavController().navigate(
                                R.id.action_detailRecipeFragment_to_favoriteRecipeFragment
                            )
                    }
                }
            }
            ivAddFavorite.setOnClickListener {
                ivAddFavorite.isEnabled = false
                val preferences: SharedPreferences = requireActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE)
                lifecycleScope.launch {
                    if (id != null && name != null && image != null) {
                        if (DatabaseHandler.existInFavorites(id) == 0) {
                            DatabaseHandler.createFavoriteRecipe(
                                FavoriteRecipeModel(
                                    id,
                                    name,
                                    image,
                                    preferences.getLong("id", 0)
                                )
                            )
                            ivAddFavorite.setImageResource(R.drawable.favorite_icon)
                        } else {
                            DatabaseHandler.deleteFavoriteRecipe(
                                FavoriteRecipeModel(
                                    id,
                                    name,
                                    image,
                                    preferences.getLong("id", 0)
                                )
                            )
                            ivAddFavorite.setImageResource(R.drawable.not_favorite_icon)
                        }
                    }
                    ivAddFavorite.isEnabled = true
                }
            }
        }
    }

    private fun observeDataIngredients(nameIng: String?, imageIng: String?) {
        viewModel.progressBarState.observe(viewLifecycleOwner) { isVisible ->
            binding.progressBar.isVisible = isVisible
        }
        viewModel.viewsState.observe(viewLifecycleOwner) { isVisible ->
            with(binding) {
                scrollView.isVisible = isVisible
            }
        }
        viewModel.ingredientDataState.observe(viewLifecycleOwner) { ingredientDataModel ->
            val listIngredients: ArrayList<IngredientModel> = arrayListOf()
            ingredientDataModel?.let { data ->
                with(binding) {
                    tvNameRecipe.text = nameIng
                    Glide.with(requireContext())
                        .load(imageIng)
                        .into(ivRecipe)
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
                    val rvIngredientsAdapter = IngredientsAdapter().apply {
                        items = listIngredients
                    }
                    with(binding) {
                        rvIngredients.adapter = rvIngredientsAdapter
                        rvIngredients.layoutManager =
                            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
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

    private fun observeDataDetails() {
        viewModel.progressBarState.observe(viewLifecycleOwner) { isVisible ->
            binding.progressBar.isVisible = isVisible
        }
        viewModel.viewsState.observe(viewLifecycleOwner) { isVisible ->
            with(binding) {
                scrollView.isVisible = isVisible
            }
        }
        viewModel.detailDataState.observe(viewLifecycleOwner) { detailDataModel ->
            val listSteps: ArrayList<StepModel> = arrayListOf()
            detailDataModel?.let { data ->
                listSteps.clear()
                val result = data.steps
                for (i in result.indices) {
                    listSteps.add(
                        StepModel(
                            result[i].number,
                            result[i].step
                        )
                    )
                }
                val rvDetailRecipeAdapter = DetailRecipeAdapter().apply {
                    items = listSteps
                }
                with(binding) {
                    rvDetail.adapter = rvDetailRecipeAdapter
                    rvDetail.layoutManager =
                        LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
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