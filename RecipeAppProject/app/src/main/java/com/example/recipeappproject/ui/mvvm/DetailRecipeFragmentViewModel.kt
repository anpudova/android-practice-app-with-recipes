package com.example.recipeappproject.ui.mvvm

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.recipeappproject.di.ViewModelArgsKeys
import com.example.recipeappproject.domain.usecase.GetIngredientsByIdUseCase
import com.example.recipeappproject.domain.usecase.GetRecipesByNameUseCase
import com.example.recipeappproject.ui.model.IngredientsDataModel
import com.example.recipeappproject.ui.model.RecipesDataModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DetailRecipeFragmentViewModel (
    private val getIngredientsUseCase: GetIngredientsByIdUseCase
) : ViewModel() {

    private val _progressBarState: MutableLiveData<Boolean> = MutableLiveData(false)
    val progressBarState: LiveData<Boolean> = _progressBarState

    private val _viewsState: MutableLiveData<Boolean> = MutableLiveData(false)
    val viewsState: LiveData<Boolean> = _viewsState

    private val _ingredientDataState: MutableLiveData<IngredientsDataModel?> = MutableLiveData(null)
    val ingredientDataState: LiveData<IngredientsDataModel?> = _ingredientDataState

    private val _errorState: MutableLiveData<Throwable> = MutableLiveData(null)
    val errorState: LiveData<Throwable> = _errorState

    fun requestIngredientsById(id: Long) {
        viewModelScope.launch {
            _progressBarState.value = true
            _viewsState.value = false
            delay(2000L)
            runCatching {
                getIngredientsUseCase(id)
            }.onSuccess { dataModel ->
                _progressBarState.value = false
                _viewsState.value = true
                _ingredientDataState.postValue(dataModel)
            }.onFailure { ex ->
                _progressBarState.value = false
                _errorState.value = ex
            }
        }
    }

    companion object {

        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {

            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val getIngredientsUseCase =
                    extras[ViewModelArgsKeys.getIngredientsByIdCaseKey]
                        ?: throw IllegalArgumentException()
                return (DetailRecipeFragmentViewModel(getIngredientsUseCase) as? T)
                    ?: throw java.lang.IllegalStateException()
            }
        }
    }
}