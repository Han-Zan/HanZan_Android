package com.kud.hanzan.domain.repository

import com.kud.hanzan.domain.model.map.Place
import com.kud.hanzan.domain.model.map.Store
import kotlinx.coroutines.flow.Flow

interface KakaoRepository {
    fun getKeywordPlace(keyword: String) : Flow<List<Place>>
    fun getRoadAddress(longitude: String, latitude: String) : Flow<String>
    fun getCategoryPlace(longitude: String, latitude: String, page: Int, currentX: Double, currentY: Double) : Flow<List<Store>>
}