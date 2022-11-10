package com.kud.hanzan.data.source.login

import android.content.ContentValues.TAG
import android.util.Log
import com.google.android.material.tabs.TabLayout.TabGravity
import com.kud.hanzan.data.remote.HanzanLoginService
import com.kud.hanzan.domain.model.UserInfo
import com.kud.hanzan.domain.model.UserResponseDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginDataSource @Inject constructor(
    private val hanzanService: HanzanLoginService
){
    suspend fun postUserInfo(userInfo: UserInfo): UserResponseDto {
        val userResponseDto : UserResponseDto = UserResponseDto(0, "error0")
        withContext(Dispatchers.IO) {
            runCatching {
                hanzanService.postUserInfo(userInfo)
            }.onSuccess {
                userResponseDto.userIdx = it.body()?.userIdx ?: 0
                userResponseDto.userToken = it.body()?.userToken ?: "error1"
            }.onFailure {
                Log.e(TAG, "Posting User Information Failure")
            }
        }
        return userResponseDto
    }

    suspend fun checkUserAccount(userId: Long): UserResponseDto {
        val userResponseDto : UserResponseDto = UserResponseDto(0, "error2")
        withContext(Dispatchers.IO) {
            runCatching {
                hanzanService.checkUserAccount(userId)
            }.onSuccess {
                userResponseDto.userIdx = it.body()?.userIdx ?: 0
                userResponseDto.userToken = it.body()?.userToken ?: "error"
            }.onFailure {
                Log.e(TAG, "Checking User Account Failure")
            }
        }
        return userResponseDto
    }
}