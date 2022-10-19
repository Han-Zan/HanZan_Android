package com.kud.hanzan.data.source.login

import android.util.Log
import com.kud.hanzan.data.remote.HanzanService
import com.kud.hanzan.domain.model.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginDataSource @Inject constructor(
    private val hanzanService: HanzanService
){
    suspend fun postUserInfo(userInfo: UserInfo): Long {
        var res : Long = 0
        withContext(Dispatchers.IO) {
            runCatching {
                hanzanService.postUserInfo(userInfo)
            }.onSuccess {
                res = it.body() ?: 0
            }.onFailure {

            }
        }
        return res
    }
}