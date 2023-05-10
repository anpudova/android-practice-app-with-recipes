package com.example.recipeappproject.data.model

import com.google.gson.annotations.SerializedName

class DetailRecipeResponse: ArrayList<DetailRecipeResponse.Result>() {

    data class Result(
        @SerializedName("name")
        val name: String?,
        @SerializedName("steps")
        val steps: List<Step>?
    )

    data class Step(
        @SerializedName("equipment")
        val equipment: List<Equipment>?,
        @SerializedName("ingredients")
        val ingredients: List<Ingredients>?,
        @SerializedName("length")
        val len: Length? = null,
        @SerializedName("number")
        val number: Int?,
        @SerializedName("step")
        val step: String?
    )

    data class Equipment(
        @SerializedName("id")
        val id: Long?,
        @SerializedName("image")
        val image: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("temperature")
        val temp: Temperature? = null
    )

    data class Ingredients(
        @SerializedName("id")
        val id: Long?,
        @SerializedName("image")
        val image: String?,
        @SerializedName("name")
        val name: String?
    )

    data class Temperature(
        @SerializedName("number")
        val num: Double?,
        @SerializedName("unit")
        val unit: String?
    )

    data class Length(
        @SerializedName("number")
        val num: Int?,
        @SerializedName("unit")
        val unit: String?
    )
}