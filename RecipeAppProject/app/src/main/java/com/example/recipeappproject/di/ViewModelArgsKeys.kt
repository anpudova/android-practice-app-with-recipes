package com.example.recipeappproject.di

import androidx.lifecycle.viewmodel.CreationExtras
import com.example.recipeappproject.domain.usecase.GetDetailRecipeByIdUseCase
import com.example.recipeappproject.domain.usecase.GetIngredientsByIdUseCase
import com.example.recipeappproject.domain.usecase.GetRecipesByNameUseCase

object ViewModelArgsKeys {

    val getRecipesByNameCaseKey = object : CreationExtras.Key<GetRecipesByNameUseCase> {}
    val getIngredientsByIdCaseKey = object : CreationExtras.Key<GetIngredientsByIdUseCase> {}
    val getDetailRecipeByIdCaseKey = object : CreationExtras.Key<GetDetailRecipeByIdUseCase> {}
}