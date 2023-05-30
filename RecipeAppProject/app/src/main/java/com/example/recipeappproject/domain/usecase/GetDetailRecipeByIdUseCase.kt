package com.example.recipeappproject.domain.usecase

import com.example.recipeappproject.domain.repository.IRecipeRepository
import com.example.recipeappproject.ui.model.DetailRecipeDataModel

class GetDetailRecipeByIdUseCase (
    private val recipeRepository: IRecipeRepository
) {
    suspend operator fun invoke(id: Long): DetailRecipeDataModel {
        return recipeRepository.getDetailRecipeById(id = id).mapDetailRecipeEntity()
    }
}