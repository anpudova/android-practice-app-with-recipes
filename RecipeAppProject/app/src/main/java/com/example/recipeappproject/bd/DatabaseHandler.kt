package com.example.recipeappproject.bd

import android.content.Context
import androidx.room.Room
import com.example.recipeappproject.bd.entity.FavoriteRecipeEntity
import com.example.recipeappproject.bd.entity.UserEntity
import com.example.recipeappproject.bd.mapper.FavoriteRecipeMapper
import com.example.recipeappproject.bd.mapper.UserMapper
import com.example.recipeappproject.bd.model.FavoriteRecipeModel
import com.example.recipeappproject.bd.model.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object DatabaseHandler {

    const val versiondb: Int = 1
    const val namedb: String = "dbRecipes"
    private var roomDatabase: InceptionDatabase? = null

    fun dbInit(appContext: Context) {
        if (roomDatabase == null) {
            roomDatabase = Room.databaseBuilder(
                appContext,
                InceptionDatabase::class.java,
                namedb
            ).build()
        }
    }

    suspend fun createUser(user: UserModel) {
        withContext(Dispatchers.IO) {
            roomDatabase?.getUserDao()?.createUser(UserMapper.mapUserEntity(user))
        }
    }

    suspend fun getUser(username: String, password: String): UserModel? {
        return withContext(Dispatchers.IO) {
            val user: UserEntity? = roomDatabase?.getUserDao()?.getUser(username, password)
            UserMapper.mapUserModel(user)
        }
    }

    suspend fun getUsername(username: String): String? {
        return withContext(Dispatchers.IO) {
            roomDatabase?.getUserDao()?.getUsername(username)
        }
    }

    suspend fun getUserByUsername(username: String): UserModel? {
        return withContext(Dispatchers.IO) {
            val user: UserEntity? = roomDatabase?.getUserDao()?.getUserByUsername(username)
            UserMapper.mapUserModel(user)
        }
    }

    suspend fun deleteUser(user: UserModel) {
        withContext(Dispatchers.IO) {
            roomDatabase?.getUserDao()?.deleteUser(UserMapper.mapUserEntity(user))
        }
    }

    suspend fun createFavoriteRecipe(recipe: FavoriteRecipeModel) {
        withContext(Dispatchers.IO) {
            roomDatabase?.getFavoriteRecipeDao()?.createFavoriteRecipe(FavoriteRecipeMapper.mapFavoriteRecipeEntity(recipe))
        }
    }

    suspend fun deleteFavoriteRecipe(recipe: FavoriteRecipeModel) {
        withContext(Dispatchers.IO) {
            roomDatabase?.getFavoriteRecipeDao()?.deleteFavoriteRecipe(FavoriteRecipeMapper.mapFavoriteRecipeEntity(recipe))
        }
    }

    suspend fun getFavoriteRecipes(idUser: Long): List<FavoriteRecipeModel>? {
        return withContext(Dispatchers.IO) {
            val result: List<FavoriteRecipeEntity>? = roomDatabase?.getFavoriteRecipeDao()?.getFavoriteRecipes(idUser)
            val list = mutableListOf<FavoriteRecipeModel>()
            result?.let { res ->
                res.forEach { item ->
                    list.add(FavoriteRecipeMapper.mapFavoriteRecipeModel(item))
                }
                list
            }
        }
    }

    suspend fun existInFavorites(id: Long): Int? {
        return withContext(Dispatchers.IO) {
            roomDatabase?.getFavoriteRecipeDao()?.existInFavorites(id)
        }
    }
}