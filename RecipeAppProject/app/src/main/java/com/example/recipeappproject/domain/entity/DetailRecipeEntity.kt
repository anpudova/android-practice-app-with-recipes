package com.example.recipeappproject.domain.entity

import com.example.recipeappproject.data.model.DetailRecipeResponse
import com.example.recipeappproject.ui.model.DetailRecipeDataModel
import com.example.recipeappproject.ui.model.StepModel

data class DetailRecipeEntity (
    val name: String? = null,
    val steps: List<Step>? = null
) {

    fun mapDetailRecipeEntity(): DetailRecipeDataModel {
        val listStep = mutableListOf<StepModel>()
        steps?.let { steps ->
            steps.forEach { item ->
                listStep.add(
                    StepModel(
                        item.number,
                        item.step
                    )
                )
            }
            return DetailRecipeDataModel(
                name.toString(),
                listStep
            )
        }
        return DetailRecipeDataModel(
            name.toString(),
            emptyList()
        )
    }

    data class Step(
        var number: Int,
        var step: String
    )
}