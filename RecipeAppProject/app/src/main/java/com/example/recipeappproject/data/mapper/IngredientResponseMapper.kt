package com.example.recipeappproject.data.mapper

import com.example.recipeappproject.data.model.IngredientResponse
import com.example.recipeappproject.data.model.RecipeResponse
import com.example.recipeappproject.domain.entity.IngredientsEntity
import com.example.recipeappproject.domain.entity.RecipesEntity

class IngredientResponseMapper {

    fun map(resp: IngredientResponse?): IngredientsEntity {
        val list = mutableListOf<IngredientsEntity.IngredientEntity>()
        return resp?.let { response ->
            with(response) {
                result?.let { ingredients ->
                    ingredients.forEach { item ->
                        list.add(IngredientsEntity.IngredientEntity(
                            item.amount?.metric?.unit.toString(),
                            item.amount?.metric?.value.orEmpty(),
                            item.name.toString()
                        )
                        )
                    }
                }
                return IngredientsEntity(list)
            }
        } ?: IngredientsEntity()
    }

    private fun Float?.orEmpty() =
        this ?: 0F
}