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

    val progressBarState: MutableLiveData<Boolean> = MutableLiveData(false)
    val recipeDataState: MutableLiveData<RecipesDataModel?> = MutableLiveData(null)
    val errorState: MutableLiveData<Throwable> = MutableLiveData(null)
    private var data_: MutableLiveData<RecipesDataModel> = MutableLiveData(null)
    var data: LiveData<RecipesDataModel> = data_

    fun requestRecipesByName(name: String) {
        viewModelScope.launch {
            progressBarState.value = true
            delay(2000L)
            runCatching {
                getRecipesUseCase(name)
            }.onSuccess { dataModel ->
                data_.value = dataModel
                progressBarState.value = false
                recipeDataState.postValue(dataModel)
            }.onFailure { ex ->
                progressBarState.value = false
                errorState.value = ex
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