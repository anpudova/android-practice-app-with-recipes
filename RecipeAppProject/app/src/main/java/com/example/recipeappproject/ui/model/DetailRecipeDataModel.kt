package com.example.recipeappproject.ui.model

import com.example.recipeappproject.domain.entity.DetailRecipeEntity

data class DetailRecipeDataModel (
    val name: String,
    val steps: List<StepModel>
)