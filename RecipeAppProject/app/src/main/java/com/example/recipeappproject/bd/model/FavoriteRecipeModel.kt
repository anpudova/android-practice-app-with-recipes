package com.example.recipeappproject.bd.model

import androidx.room.PrimaryKey

class FavoriteRecipeModel (
    val id: Long,
    val name: String,
    val image: String,
    val idUser: Long
)