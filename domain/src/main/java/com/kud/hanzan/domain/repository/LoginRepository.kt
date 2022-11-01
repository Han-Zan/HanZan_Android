package com.kud.hanzan.domain.repository

import com.kud.hanzan.domain.model.UserInfo
import com.kud.hanzan.domain.model.UserResponseDto

interface LoginRepository {
    suspend fun postUserInfo(userInfo: UserInfo) : UserResponseDto?
    suspend fun checkUserAccount(userId: Long): UserResponseDto?
}