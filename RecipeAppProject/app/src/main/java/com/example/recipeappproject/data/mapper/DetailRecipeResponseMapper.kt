package com.example.recipeappproject.data.mapper

import android.util.Log
import com.example.recipeappproject.data.model.DetailRecipeResponse
import com.example.recipeappproject.data.model.IngredientResponse
import com.example.recipeappproject.domain.entity.DetailRecipeEntity
import com.example.recipeappproject.domain.entity.IngredientsEntity

class DetailRecipeResponseMapper {

    fun map(resp: DetailRecipeResponse?): DetailRecipeEntity {
        val listStep = mutableListOf<DetailRecipeEntity.Step>()
        return resp?.let { response ->
            with(response) {
                get(0).steps?.let { steps ->
                    for (i in steps.indices) {
                        listStep.add(
                            DetailRecipeEntity.Step(
                                steps[i].number!!,
                                steps[i].step.toString()
                            )
                        )
                    }
                }
                DetailRecipeEntity(
                    get(0).name.toString(),
                    listStep)
            }
        } ?: DetailRecipeEntity(
            "",
            emptyList()
        )
    }
}