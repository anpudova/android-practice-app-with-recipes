package com.example.recipeappproject.domain.usecase

import com.example.recipeappproject.domain.repository.IRecipeRepository
import com.example.recipeappproject.ui.model.IngredientsDataModel
import com.example.recipeappproject.ui.model.RecipesDataModel

class GetIngredientsByIdUseCase (
    private val recipeRepository: IRecipeRepository
) {
    suspend operator fun invoke(id: Long): IngredientsDataModel {
        return recipeRepository.getIngredientsById(id = id).mapIngredientsEntity()
    }
}