package com.example.recipeappproject.data.repository

import com.example.recipeappproject.data.mapper.DetailRecipeResponseMapper
import com.example.recipeappproject.data.mapper.IngredientResponseMapper
import com.example.recipeappproject.data.mapper.RecipeResponseMapper
import com.example.recipeappproject.data.network.RecipeApiService
import com.example.recipeappproject.domain.entity.DetailRecipeEntity
import com.example.recipeappproject.domain.entity.IngredientsEntity
import com.example.recipeappproject.domain.repository.IRecipeRepository
import com.example.recipeappproject.domain.entity.RecipesEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeRepository (
    private val remoteSource: RecipeApiService,
    private val localSource: Any,
    private val recipeResponseMapper: RecipeResponseMapper,
    private val ingredientResponseMapper: IngredientResponseMapper,
    private val detailRecipeResponseMapper: DetailRecipeResponseMapper
): IRecipeRepository {

    override suspend fun getRecipesByName(recipe: String, diet: String): RecipesEntity {
        return withContext(Dispatchers.IO) {
            (recipeResponseMapper::map)(remoteSource.getRecipeByName(recipe = recipe, diet = diet))
        }
    }

    override suspend fun getIngredientsById(id: Long): IngredientsEntity {
        return withContext(Dispatchers.IO) {
            (ingredientResponseMapper::map)(remoteSource.getIngredientsById(id = id))
        }
    }

    override suspend fun getDetailRecipeById(id: Long): DetailRecipeEntity {
        return withContext(Dispatchers.IO) {
            (detailRecipeResponseMapper::map)(remoteSource.getDetailRecipeById(id = id))
        }
    }
}