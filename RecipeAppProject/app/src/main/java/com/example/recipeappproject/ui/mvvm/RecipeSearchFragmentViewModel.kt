package com.example.recipeappproject.ui.mvvm

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.recipeappproject.di.ViewModelArgsKeys
import com.example.recipeappproject.domain.usecase.GetRecipesByNameUseCase
import com.example.recipeappproject.ui.model.RecipesDataModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RecipeSearchFragmentViewModel (
    private val getRecipesUseCase: GetRecipesByNameUseCase
) : ViewModel() {

    private val _progressBarState: MutableLiveData<Boolean> = MutableLiveData(false)
    val progressBarState: LiveData<Boolean> = _progressBarState

    private val _recipeDataState: MutableLiveData<RecipesDataModel?> = MutableLiveData(null)
    val recipeDataState: LiveData<RecipesDataModel?> = _recipeDataState

    private val _errorState: MutableLiveData<Throwable> = MutableLiveData(null)
    val errorState: LiveData<Throwable> = _errorState

    fun requestRecipesByName(name: String) {
        viewModelScope.launch {
            _progressBarState.value = true
            delay(2000L)
            runCatching {
                getRecipesUseCase(name)
            }.onSuccess { dataModel ->
                _progressBarState.value = false
                _recipeDataState.postValue(dataModel)
            }.onFailure { ex ->
                _progressBarState.value = false
                _errorState.value = ex
            }
        }
    }

    companion object {

        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {

            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val getRecipesUseCase =
                    extras[ViewModelArgsKeys.getRecipesByNameCaseKey]
                        ?: throw IllegalArgumentException()
                return (RecipeSearchFragmentViewModel(getRecipesUseCase) as? T)
                    ?: throw java.lang.IllegalStateException()
            }
        }
    }
}