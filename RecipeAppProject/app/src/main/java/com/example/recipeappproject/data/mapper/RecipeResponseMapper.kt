package com.example.recipeappproject.data.mapper

import com.example.recipeappproject.data.model.RecipeResponse
import com.example.recipeappproject.domain.entity.RecipesEntity

class RecipeResponseMapper {

    fun map(resp: RecipeResponse?): RecipesEntity {
        val list = mutableListOf<RecipesEntity.RecipeEntity>()
        return resp?.let { response ->
            with(response) {
                result?.let { res ->
                    for(i in res.indices) {
                        list.add(i, RecipesEntity.RecipeEntity(
                            res[i].id!!,
                            res[i].title.toString(),
                            res[i].image.toString(),
                            res[i].imageType.toString()
                        )
                        )
                    }
                }
                return RecipesEntity(list)
            }
        } ?: RecipesEntity(null)
    }
}