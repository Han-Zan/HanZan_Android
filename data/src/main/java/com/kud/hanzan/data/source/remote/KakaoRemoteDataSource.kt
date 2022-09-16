package com.kud.hanzan.data.source.remote

import com.kud.hanzan.data.entity.place.PlaceKeywordResult
import com.kud.hanzan.data.remote.KakaoService
import com.kud.hanzan.data.source.KakaoDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class KakaoRemoteDataSource @Inject constructor(
    private val kakaoService: KakaoService
) : KakaoDataSource {
    override suspend fun getKeywordPlace(
        keyword: String
    ): Flow<PlaceKeywordResult> =
        flow {
            emit(kakaoService.getKeywordPlace(keyword))
        }
}
