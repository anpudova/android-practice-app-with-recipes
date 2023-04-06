package com.example.recipeappproject.data.network

import com.example.recipeappproject.data.model.IngredientResponse
import com.example.recipeappproject.data.model.RecipeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApiService {

    @GET("recipes/complexSearch")
    suspend fun getRecipeByName(
        @Query("query") recipe: String
    ): RecipeResponse

    @GET("/recipes/{id}/ingredientWidget.json")
    suspend fun getIngredientsById(
        @Path("id") id: Long
    ): IngredientResponse

}