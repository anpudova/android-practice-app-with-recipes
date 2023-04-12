package com.example.recipeappproject.data.mapper

import android.util.Log
import com.example.recipeappproject.data.model.DetailRecipeResponse
import com.example.recipeappproject.data.model.IngredientResponse
import com.example.recipeappproject.domain.entity.DetailRecipeEntity
import com.example.recipeappproject.domain.entity.IngredientsEntity

class DetailRecipeResponseMapper {

    fun map(resp: DetailRecipeResponse?): DetailRecipeEntity {
        var listStep = mutableListOf<DetailRecipeEntity.Step>()
        return resp?.let { response ->
            with(response) {
                println("TAAAG " + get(0).steps?.get(0)?.step.toString())
                if (get(0).steps != null) {
                    var list = get(0).steps
                    for (j in list!!.indices) {
                        listStep.add(
                            DetailRecipeEntity.Step(
                                list[j].number!!,
                                list[j].step.toString()
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