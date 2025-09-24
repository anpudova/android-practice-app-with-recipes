package com.example.recipeappproject.bd.dao

import androidx.room.*
import com.example.recipeappproject.bd.entity.FavoriteRecipeEntity

@Dao
interface FavoriteRecipeDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun createFavoriteRecipe(recipe: FavoriteRecipeEntity)

    @Delete
    suspend fun deleteFavoriteRecipe(recipe: FavoriteRecipeEntity)

    @Query("select * from favorite_recipes where id_user = :idUser")
    suspend fun getFavoriteRecipes(idUser: Long): List<FavoriteRecipeEntity>?

    @Query("select count(*) from favorite_recipes where id = :id")
    suspend fun existInFavorites(id: Long): Int?
}