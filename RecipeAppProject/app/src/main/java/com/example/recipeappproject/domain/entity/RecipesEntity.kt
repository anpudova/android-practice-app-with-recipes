package com.example.recipeappproject.domain.entity

import com.example.recipeappproject.ui.model.RecipeModel
import com.example.recipeappproject.ui.model.RecipesDataModel

data class RecipesEntity (
    val recipes: List<RecipeEntity>? = null
) {
    fun mapRecipeEntity(): RecipesDataModel {
        var list = mutableListOf<RecipeModel>()
        if (recipes != null) {
            for(i in recipes.indices) {
                list.add(i,
                    RecipeModel(
                        recipes[i].id,
                        recipes[i].title,
                        recipes[i].image,
                        recipes[i].imageType
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