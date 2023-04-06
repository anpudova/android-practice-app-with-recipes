package com.example.recipeappproject.domain.entity

import com.example.recipeappproject.ui.model.IngredientModel
import com.example.recipeappproject.ui.model.IngredientsDataModel
import com.example.recipeappproject.ui.model.RecipeModel
import com.example.recipeappproject.ui.model.RecipesDataModel

data class IngredientsEntity (
    val ingredients: List<IngredientEntity>? = null
) {

    fun mapIngredientsEntity(): IngredientsDataModel {
        var list = mutableListOf<IngredientModel>()
        if (ingredients != null) {
            for(i in ingredients.indices) {
                list.add(i,
                    IngredientModel(
                        ingredients[i].unit,
                        ingredients[i].value,
                        ingredients[i].name
                    )
                )
            }
            return IngredientsDataModel(list)
        }
        return IngredientsDataModel(emptyList())
    }

    data class IngredientEntity (
        var unit: String,
        var value: Float,
        var name: String
    )
}