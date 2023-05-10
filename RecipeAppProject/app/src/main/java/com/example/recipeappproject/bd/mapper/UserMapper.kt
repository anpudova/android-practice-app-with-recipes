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
        if (user != null) {
            with(user) {
                return UserModel(
                    id, username, password
                )
            }
        } else {
            return null
        }
    }
}