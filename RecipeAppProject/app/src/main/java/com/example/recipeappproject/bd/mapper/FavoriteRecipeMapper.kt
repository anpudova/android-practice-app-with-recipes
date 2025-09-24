package com.example.recipeappproject.bd.mapper

import com.example.recipeappproject.bd.entity.FavoriteRecipeEntity
import com.example.recipeappproject.bd.entity.UserEntity
import com.example.recipeappproject.bd.model.FavoriteRecipeModel
import com.example.recipeappproject.bd.model.UserModel

object FavoriteRecipeMapper {

    fun mapFavoriteRecipeEntity(recipe: FavoriteRecipeModel): FavoriteRecipeEntity {
        with(recipe) {
            return FavoriteRecipeEntity(
                id, name, image, idUser
            )
        }
    }

    fun mapFavoriteRecipeModel(recipe: FavoriteRecipeEntity): FavoriteRecipeModel {
        with(recipe) {
            return FavoriteRecipeModel(
                id, name, image, idUser
            )
        }
    }

}