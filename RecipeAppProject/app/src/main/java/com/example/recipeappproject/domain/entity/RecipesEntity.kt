package com.example.recipeappproject.domain.entity

import com.example.recipeappproject.ui.model.RecipeModel
import com.example.recipeappproject.ui.model.RecipesDataModel

data class RecipesEntity (
    val recipes: List<RecipeEntity>? = null
) {
    fun mapRecipeEntity(): RecipesDataModel {
        val list = mutableListOf<RecipeModel>()
        recipes?.let { recipes ->
            recipes.forEach { item ->
                list.add(RecipeModel(
                    item.id,
                    item.title,
                    item.image,
                    item.imageType
                    )
                )
            }
            return RecipesDataModel(list)
        }
        return RecipesDataModel(emptyList())
    }

    data class RecipeEntity (
        var id: Long,
        var title: String,
        var image: String,
        var imageType: String
    )
}