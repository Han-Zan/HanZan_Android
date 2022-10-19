package com.kud.hanzan.domain.repository

import com.kud.hanzan.domain.model.UserInfo

interface LoginRepository {
    suspend fun postUserInfo(userInfo: UserInfo) : Long
}