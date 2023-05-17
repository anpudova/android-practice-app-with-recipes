package com.example.recipeappproject.ui.mvvm

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.recipeappproject.di.ViewModelArgsKeys
import com.example.recipeappproject.domain.usecase.GetDetailRecipeByIdUseCase
import com.example.recipeappproject.domain.usecase.GetIngredientsByIdUseCase
import com.example.recipeappproject.domain.usecase.GetRecipesByNameUseCase
import com.example.recipeappproject.ui.model.DetailRecipeDataModel
import com.example.recipeappproject.ui.model.IngredientsDataModel
import com.example.recipeappproject.ui.model.RecipesDataModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DetailRecipeFragmentViewModel (
    private val getIngredientsUseCase: GetIngredientsByIdUseCase,
    private val getDetailUseCase: GetDetailRecipeByIdUseCase
) : ViewModel() {

    val progressBarState: MutableLiveData<Boolean> = MutableLiveData(false)
    val viewsState: MutableLiveData<Boolean> = MutableLiveData(false)
    val ingredientDataState: MutableLiveData<IngredientsDataModel?> = MutableLiveData(null)
    val detailDataState: MutableLiveData<DetailRecipeDataModel?> = MutableLiveData(null)
    val errorState: MutableLiveData<Throwable> = MutableLiveData(null)

    fun requestIngredientsById(id: Long) {
        viewModelScope.launch {
            progressBarState.value = true
            viewsState.value = false
            delay(2000L)
            runCatching {
                getIngredientsUseCase(id)
            }.onSuccess { dataModel ->
                progressBarState.value = false
                viewsState.value = true
                ingredientDataState.postValue(dataModel)
            }.onFailure { ex ->
                progressBarState.value = false
                errorState.value = ex
            }
        }
    }

    fun requestDetailRecipeById(id: Long) {
        viewModelScope.launch {
            progressBarState.value = true
            viewsState.value = false
            delay(TIME_MILLIS)
            runCatching {
                getDetailUseCase(id)
            }.onSuccess { dataModel ->
                progressBarState.value = false
                viewsState.value = true
                detailDataState.postValue(dataModel)
            }.onFailure { ex ->
                progressBarState.value = false
                errorState.value = ex
            }
        }
    }

    companion object {

        const val TIME_MILLIS = 2000L

        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {

            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val getIngredientsUseCase =
                    extras[ViewModelArgsKeys.getIngredientsByIdCaseKey]
                        ?: throw IllegalArgumentException()
                val getDetailUseCase =
                    extras[ViewModelArgsKeys.getDetailRecipeByIdCaseKey]
                        ?: throw IllegalArgumentException()
                return (DetailRecipeFragmentViewModel(getIngredientsUseCase, getDetailUseCase) as? T)
                    ?: throw java.lang.IllegalStateException()
            }
        }
    }
}