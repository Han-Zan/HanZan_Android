package com.kud.hanzan.data.source.login

import android.util.Log
import com.kud.hanzan.data.remote.HanzanService
import com.kud.hanzan.domain.model.UserInfo
import com.kud.hanzan.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val dataSource: LoginDataSource
): LoginRepository {
    override suspend fun postUserInfo(userInfo: UserInfo): Long {
        Log.e("shit", "머선일이고")
        return dataSource.postUserInfo(userInfo)
    }
}