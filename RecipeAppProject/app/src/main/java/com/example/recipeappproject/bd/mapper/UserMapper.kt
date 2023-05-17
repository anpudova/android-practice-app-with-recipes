package com.example.recipeappproject.bd.mapper

import com.example.recipeappproject.bd.entity.UserEntity
import com.example.recipeappproject.bd.model.UserModel

object UserMapper {

    fun mapUserEntity(user: UserModel): UserEntity {
        with(user) {
            return UserEntity(
                id, username, password
            )
        }
    }

    fun mapUserModel(user: UserEntity?): UserModel? {
        return user?.let {
            UserModel(it.id, it.username, it.password)
        }
    }
}