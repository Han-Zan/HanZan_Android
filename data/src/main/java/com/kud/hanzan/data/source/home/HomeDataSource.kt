package com.kud.hanzan.data.source.home

import com.kud.hanzan.domain.model.HomeData
import kotlinx.coroutines.flow.Flow

interface HomeDataSource {
    fun getHomeData(userIdx: Long) : Flow<HomeData>
}