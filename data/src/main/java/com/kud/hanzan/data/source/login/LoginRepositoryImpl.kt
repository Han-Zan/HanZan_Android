package com.kud.hanzan.data.source.login

import android.content.ContentValues.TAG
import android.util.Log
import com.kud.hanzan.data.remote.HanzanService
import com.kud.hanzan.domain.model.UserInfo
import com.kud.hanzan.domain.model.UserResponseDto
import com.kud.hanzan.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val dataSource: LoginDataSource
): LoginRepository {
    override suspend fun postUserInfo(userInfo: UserInfo): UserResponseDto? {
        return dataSource.postUserInfo(userInfo)
    }

    override suspend fun checkUserAccount(userId: Long): UserResponseDto? {
        return dataSource.checkUserAccount(userId)
    }
}