package com.kud.hanzan.data.source.profile

import com.kud.hanzan.domain.model.User
import com.kud.hanzan.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val dataSource: ProfileDataSource
): ProfileRepository {
    override suspend fun getUser(userId: Long): User {
        return dataSource.getUser(userId)
    }

    override suspend fun changeUserNickName(userId: Long, userName: String): String {
        return dataSource.changeUserNickName(userId, userName)
    }

    override suspend fun changeUserProfile(userId: Long, userImg: String): String {
        return dataSource.changeUserProfile(userId, userImg)
    }

    override suspend fun deleteUser(userId: Long): String {
        return dataSource.deleteUser(userId)
    }
}