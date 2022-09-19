package com.kud.hanzan.domain.repository

import com.kud.hanzan.domain.model.Place
import kotlinx.coroutines.flow.Flow

interface KakaoRepository {
    fun getKeywordPlace(keyword: String) : Flow<List<Place>>
}