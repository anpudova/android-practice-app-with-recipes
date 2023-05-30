package com.example.recipeappproject.domain.usecase

import com.example.recipeappproject.domain.repository.IRecipeRepository
import com.example.recipeappproject.ui.model.RecipesDataModel

class GetRecipesByNameUseCase (
    private val recipeRepository: IRecipeRepository
) {
    suspend operator fun invoke(recipe: String, diet: String): RecipesDataModel {
        return recipeRepository.getRecipesByName(recipe, diet).mapRecipeEntity()
    }
}