package com.example.recipeappproject.data.network

import com.example.recipeappproject.data.model.DetailRecipeResponse
import com.example.recipeappproject.data.model.IngredientResponse
import com.example.recipeappproject.data.model.RecipeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApiService {

    @GET("recipes/complexSearch?number=100")
    suspend fun getRecipeByName(
        @Query("query") recipe: String,
        @Query("diet") diet: String
    ): RecipeResponse

    @GET("/recipes/{id}/ingredientWidget.json")
    suspend fun getIngredientsById(
        @Path("id") id: Long
    ): IngredientResponse

    @GET("/recipes/{id}/analyzedInstructions?stepBreakdown=true")
    suspend fun getDetailRecipeById(
        @Path("id") id: Long
    ): DetailRecipeResponse

}