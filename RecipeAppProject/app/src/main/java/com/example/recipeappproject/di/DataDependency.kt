package com.example.recipeappproject.di

import com.example.recipeappproject.data.mapper.IngredientResponseMapper
import com.example.recipeappproject.data.mapper.RecipeResponseMapper
import com.example.recipeappproject.data.network.RecipeApiService
import com.example.recipeappproject.data.network.RecipeService
import com.example.recipeappproject.data.repository.RecipeRepository
import com.example.recipeappproject.domain.repository.IRecipeRepository
import com.example.recipeappproject.domain.usecase.GetIngredientsByIdUseCase
import com.example.recipeappproject.domain.usecase.GetRecipesByNameUseCase

object DataDependency {

    private val recipeResponseMapper = RecipeResponseMapper()
    private val ingredientResponseMapper = IngredientResponseMapper()
    private val recipeApiService: RecipeApiService = RecipeService.getInstance()
    private val recipeRepository: IRecipeRepository = RecipeRepository(
        remoteSource = recipeApiService,
        localSource = Any(),
        recipeResponseMapper = recipeResponseMapper,
        ingredientResponseMapper = ingredientResponseMapper
    )
    val getRecipesByNameUseCase = GetRecipesByNameUseCase(recipeRepository)
    val getIngredientsByIdUseCase = GetIngredientsByIdUseCase(recipeRepository)
}