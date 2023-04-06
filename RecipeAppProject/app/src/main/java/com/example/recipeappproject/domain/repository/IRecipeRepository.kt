package com.example.recipeappproject.domain.repository

import com.example.recipeappproject.domain.entity.IngredientsEntity
import com.example.recipeappproject.domain.entity.RecipesEntity

interface IRecipeRepository {

    suspend fun getRecipesByName(recipe: String): RecipesEntity
    suspend fun getIngredientsById(id: Long): IngredientsEntity
}