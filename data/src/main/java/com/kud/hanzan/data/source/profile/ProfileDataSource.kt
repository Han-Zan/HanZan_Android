package com.kud.hanzan.data.source.profile

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.kud.hanzan.data.remote.HanzanService
import com.kud.hanzan.domain.model.User
import com.kud.hanzan.domain.model.UserResponseDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProfileDataSource @Inject constructor(
    private val hanzanService: HanzanService
){
    suspend fun getUser(userId: Long) : User {
        val userRes : User = User(0, 0, true, "", "", "", 0, "")
        withContext(Dispatchers.IO) {
            runCatching {
                hanzanService.getUser(userId)
            }.onSuccess {
                userRes.id = it.body()?.id ?: 0
                userRes.kakaoId = it.body()?.kakaoId ?: 0
                userRes.male = it.body()?.male ?: true
                userRes.nickname = it.body()?.nickname ?: ""
                userRes.profileimage = it.body()?.profileimage ?: ""
                userRes.sbti = it.body()?.sbti ?: ""
                userRes.userage = it.body()?.userage ?: 0
                userRes.username = it.body()?.username ?: ""
            }.onFailure {
                Log.e(TAG, "Getting User Information Failure")
                Log.e(TAG, it.toString())
            }
        }
        return userRes
    }

    suspend fun changeUserNickName(userId: Long, userName: String) : String {
        var putResponse : String = ""
        withContext(Dispatchers.IO) {
            runCatching {
                hanzanService.changeUserNickName(userId, userName)
            }.onSuccess {
                putResponse = "Success"
                Log.e(TAG, "User Nickname Change Success")
            }.onFailure {
                putResponse = "Failure"
                Log.e(TAG, "User Nickname Change Failure")
            }
        }
        return putResponse
    }

    suspend fun changeUserProfile(userId: Long, userImg: String) : String {
        var putResponse : String = ""
        withContext(Dispatchers.IO) {
            runCatching {
                hanzanService.changeUserProfile(userId, userImg)
            }.onSuccess {
                putResponse = "Success"
                Log.e(TAG, "User Profile Image Change Success")
            }.onFailure {
                putResponse = "Failure"
                Log.e(TAG, "User Profile Image Change Failure")
            }
        }
        return putResponse
    }

    suspend fun deleteUser(userId: Long): String {
        var deleteResponse : String = ""
        withContext(Dispatchers.IO) {
            runCatching {
                hanzanService.deleteUser(userId)
            }.onSuccess {
                deleteResponse = "Success"
                Log.e(TAG, "User Delete Success")
            }.onFailure {
                deleteResponse = "Failure"
                Log.e(TAG, "User Delete Failure")
            }
        }
        return deleteResponse
    }
}