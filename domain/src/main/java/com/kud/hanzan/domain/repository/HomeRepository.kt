package com.kud.hanzan.domain.repository

import com.kud.hanzan.domain.model.HomeData
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getHomeData(userIdx: Long) : Flow<HomeData>
}