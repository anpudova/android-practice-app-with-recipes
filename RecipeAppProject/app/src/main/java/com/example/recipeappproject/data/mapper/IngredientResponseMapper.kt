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
                result?.let { res ->
                    for(i in res.indices) {
                        list.add(i, IngredientsEntity.IngredientEntity(
                            res[i].amount?.metric?.unit.toString(),
                            res[i].amount?.metric?.value.orEmpty(),
                            res[i].name.toString()
                        )
                        )
                    }
                }
                return IngredientsEntity(list)
            }
        } ?: IngredientsEntity(null)
    }

    private fun Float?.orEmpty() =
        this ?: 0F
}