package com.kud.hanzan.data.source.remote

import com.kud.hanzan.data.entity.place.PlaceKeywordResult
import com.kud.hanzan.data.entity.place.PlaceResult
import com.kud.hanzan.data.remote.KakaoService
import com.kud.hanzan.data.source.KakaoDataSource
import com.kud.hanzan.data.utils.base.BaseRepository
import com.kud.hanzan.domain.utils.error.RemoteErrorEmitter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class KakaoRemoteDataSource @Inject constructor(
    private val kakaoService: KakaoService
) : BaseRepository(), KakaoDataSource {
    override suspend fun getKeywordPlace(emitter: RemoteErrorEmitter, keyword: String) : Flow<PlaceKeywordResult>? {
        return safeApiCall(emitter){
            flow {
                emit(kakaoService.getKeywordPlace(keyword))
            }
        }
    }
}