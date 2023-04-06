package com.example.recipeappproject.data.mapper

import com.example.recipeappproject.data.model.RecipeResponse
import com.example.recipeappproject.domain.entity.RecipesEntity

class RecipeResponseMapper {

    fun map(resp: RecipeResponse?): RecipesEntity {
        var list = mutableListOf<RecipesEntity.RecipeEntity>()
        return resp?.let { response ->
            with(response) {
                if (result != null) {
                    for(i in result.indices) {
                        list.add(i, RecipesEntity.RecipeEntity(
                            result[i].id!!,
                            result[i].title.toString(),
                            result[i].image.toString(),
                            result[i].imageType.toString()
                            )
                        )
                    }
                }
                return RecipesEntity(list)
            }
        } ?: RecipesEntity(null)
    }
}