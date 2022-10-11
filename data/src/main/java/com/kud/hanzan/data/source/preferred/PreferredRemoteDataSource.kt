package com.kud.hanzan.data.source.preferred

import com.kud.hanzan.data.entity.preferred.CombResult
import com.kud.hanzan.data.remote.HanzanService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PreferredRemoteDataSource @Inject constructor(
    private val hanzanService: HanzanService
) : PreferredDataSource {
    override fun getPreferredComb(
        userId: Long
    ): Flow<CombResult> = flow {
        emit(hanzanService.getPreferredAlcohol(userId))
    }.flowOn(Dispatchers.IO)
}