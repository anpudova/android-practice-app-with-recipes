package com.example.recipeappproject.data.mapper

import com.example.recipeappproject.data.model.RecipeResponse
import com.example.recipeappproject.domain.entity.RecipesEntity

class RecipeResponseMapper {

    fun map(resp: RecipeResponse?): RecipesEntity {
        val list = mutableListOf<RecipesEntity.RecipeEntity>()
        return resp?.let { response ->
            with(response) {
                result?.let { recipes ->
                    recipes.forEach { item ->
                        item.id?.let { id ->
                            RecipesEntity.RecipeEntity(
                                id,
                                item.title.toString(),
                                item.image.toString(),
                                item.imageType.toString()
                            )
                        }?.let { recipeEntity ->
                            list.add(
                                recipeEntity
                            )
                        }
                    }
                }
                return RecipesEntity(list)
            }
        } ?: RecipesEntity()
    }
}