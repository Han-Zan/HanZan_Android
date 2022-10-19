package com.kud.hanzan.data.source.login

import com.kud.hanzan.data.remote.HanzanService
import com.kud.hanzan.domain.model.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginDataSource @Inject constructor(
    private val hanzanService: HanzanService
){
    suspend fun postUserInfo(userInfo: UserInfo): Long {
        return withContext(Dispatchers.IO) {
            val res = hanzanService.postUserInfo(userInfo)
            if (res.isSuccessful) {
                return@withContext res.body()!!
            } else {
                return@withContext 0
            }
        }

    }
}