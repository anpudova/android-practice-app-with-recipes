package com.example.recipeappproject.bd

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.recipeappproject.bd.dao.FavoriteRecipeDao
import com.example.recipeappproject.bd.dao.UserDao
import com.example.recipeappproject.bd.entity.FavoriteRecipeEntity
import com.example.recipeappproject.bd.entity.UserEntity

@Database(entities = [UserEntity::class, FavoriteRecipeEntity::class], version = DatabaseHandler.versiondb)
abstract class InceptionDatabase: RoomDatabase() {

    abstract fun getUserDao(): UserDao
    abstract fun getFavoriteRecipeDao(): FavoriteRecipeDao

}