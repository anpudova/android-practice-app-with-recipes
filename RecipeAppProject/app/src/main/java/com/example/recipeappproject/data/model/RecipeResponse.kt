package com.example.recipeappproject.data.model

import com.google.gson.annotations.SerializedName

data class RecipeResponse (
    @SerializedName("results")
    val result: List<Result>? = null

) {
    data class Result(
        @SerializedName("id")
        val id: Long?,
        @SerializedName("title")
        val title: String?,
        @SerializedName("image")
        val image: String?,
        @SerializedName("imageType")
        val imageType: String?
    )
}