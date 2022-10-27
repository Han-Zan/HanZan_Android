package com.kud.hanzan.data.source.home

import com.kud.hanzan.data.remote.HanzanService
import com.kud.hanzan.domain.model.HomeData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HomeRemoteDataSource @Inject constructor(
    private val hanzanService: HanzanService
) : HomeDataSource{
    override fun getHomeData(
        userIdx: Long)
    : Flow<HomeData> = flow {
        emit(hanzanService.getHomeData(userIdx))
    }.flowOn(Dispatchers.IO)
}