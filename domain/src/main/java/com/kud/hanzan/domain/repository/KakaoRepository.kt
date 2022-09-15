package com.kud.hanzan.domain.repository

import com.kud.hanzan.domain.model.Place
import com.kud.hanzan.domain.utils.error.RemoteErrorEmitter
import kotlinx.coroutines.flow.Flow

interface KakaoRepository {
    suspend fun getKeywordPlace(emitter: RemoteErrorEmitter, keyword: String) : Flow<List<Place>>
}