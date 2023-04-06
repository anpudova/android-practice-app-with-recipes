package com.example.recipeappproject.data.mapper

import com.example.recipeappproject.data.model.IngredientResponse
import com.example.recipeappproject.data.model.RecipeResponse
import com.example.recipeappproject.domain.entity.IngredientsEntity
import com.example.recipeappproject.domain.entity.RecipesEntity

class IngredientResponseMapper {

    fun map(resp: IngredientResponse?): IngredientsEntity {
        var list = mutableListOf<IngredientsEntity.IngredientEntity>()
        return resp?.let { response ->
            with(response) {
                if (result != null) {
                    for(i in result.indices) {
                        list.add(i, IngredientsEntity.IngredientEntity(
                            result[i].amount?.metric?.unit.toString(),
                            result[i].amount?.metric?.value!!,
                            result[i].name.toString()
                        )
                        )
                    }
                }
                return IngredientsEntity(list)
            }
        } ?: IngredientsEntity(null)
    }
}