package com.example.recipeappproject.bd.entity

import androidx.room.*

@Entity(
    tableName = "favorite_recipes",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["id_user"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class FavoriteRecipeEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val image: String,
    @ColumnInfo(name = "id_user") val idUser: Long
)