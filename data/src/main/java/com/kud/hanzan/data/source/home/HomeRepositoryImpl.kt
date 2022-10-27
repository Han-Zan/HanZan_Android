package com.kud.hanzan.data.source.home

import com.kud.hanzan.domain.model.HomeData
import com.kud.hanzan.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeRemoteDataSource: HomeRemoteDataSource
) : HomeRepository{
    override fun getHomeData(
        userIdx: Long
    ): Flow<HomeData> = homeRemoteDataSource.getHomeData(userIdx)
}