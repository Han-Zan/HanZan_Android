package com.kud.hanzan.data.source.remote

import com.kud.hanzan.data.entity.place.PlaceKeywordResult
import com.kud.hanzan.data.entity.place.RoadAddressResult
import com.kud.hanzan.data.remote.KakaoService
import com.kud.hanzan.data.source.KakaoDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class KakaoRemoteDataSource @Inject constructor(
    private val kakaoService: KakaoService
) : KakaoDataSource {
    override fun getKeywordPlace(
        keyword: String
    ): Flow<PlaceKeywordResult> = flow {
            emit(kakaoService.getKeywordPlace(keyword))
    }.flowOn(Dispatchers.IO)

    override fun getRoadAddress(
        longitude: String, latitude: String
    ): Flow<RoadAddressResult> = flow {
        val res = kakaoService.getRoadAddress(longitude, latitude)
        res.documents[0].road_address?.let { emit(res) }
    }.flowOn(Dispatchers.IO)

}
