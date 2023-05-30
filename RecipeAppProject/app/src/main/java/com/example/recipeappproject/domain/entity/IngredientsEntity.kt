package com.example.recipeappproject.domain.entity

import com.example.recipeappproject.ui.model.IngredientModel
import com.example.recipeappproject.ui.model.IngredientsDataModel
import com.example.recipeappproject.ui.model.RecipeModel
import com.example.recipeappproject.ui.model.RecipesDataModel

data class IngredientsEntity (
    val ingredients: List<IngredientEntity>? = null
) {

    fun mapIngredientsEntity(): IngredientsDataModel {
        val list = mutableListOf<IngredientModel>()
        ingredients?.let { ingredients ->
            ingredients.forEach { item ->
                list.add(IngredientModel(
                    item.unit,
                    item.value,
                    item.name
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