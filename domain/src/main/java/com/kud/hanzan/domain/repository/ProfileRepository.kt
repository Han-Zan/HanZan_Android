package com.kud.hanzan.domain.repository

import com.kud.hanzan.domain.model.User

interface ProfileRepository {
    suspend fun getUser(userId: Long) : User
    suspend fun changeUserNickName(userId: Long, userName: String) : String
    suspend fun changeUserProfile(userId: Long, userImg: String) : String
    suspend fun deleteUser(userId: Long): String
}