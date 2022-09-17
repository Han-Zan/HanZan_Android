package com.kud.hanzan.data.source

import com.kud.hanzan.data.entity.place.PlaceKeywordResult
import kotlinx.coroutines.flow.Flow

interface KakaoDataSource {
    fun getKeywordPlace(keyword: String) : Flow<PlaceKeywordResult>
}