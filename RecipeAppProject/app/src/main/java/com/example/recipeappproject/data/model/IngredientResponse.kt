package com.example.recipeappproject.data.model

import com.google.gson.annotations.SerializedName

data class IngredientResponse (
    @SerializedName("ingredients")
    val result: List<Result>? = null

) {
    data class Result(
        @SerializedName("amount")
        val amount: Amount?,
        @SerializedName("image")
        val image: String?,
        @SerializedName("name")
        val name: String?
    )

    data class Amount(
        @SerializedName("metric")
        val metric: UnitValue?,
        @SerializedName("us")
        val us: UnitValue?
    )

    data class UnitValue(
        @SerializedName("unit")
        val unit: String?,
        @SerializedName("value")
        val value: Float?
    )
}