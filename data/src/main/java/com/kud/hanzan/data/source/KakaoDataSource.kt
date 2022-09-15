package com.kud.hanzan.data.source

import com.kud.hanzan.data.entity.place.PlaceKeywordResult
import com.kud.hanzan.domain.utils.error.RemoteErrorEmitter
import kotlinx.coroutines.flow.Flow

interface KakaoDataSource {
    suspend fun getKeywordPlace(emitter: RemoteErrorEmitter, keyword: String) : Flow<PlaceKeywordResult>?
}